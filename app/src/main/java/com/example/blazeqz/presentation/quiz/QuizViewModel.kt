package com.example.blazeqz.presentation.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.blazeqz.domain.model.UserAnswer
import com.example.blazeqz.domain.repository.QuizQuestionRepository
import com.example.blazeqz.domain.repository.QuizTopicRepository
import com.example.blazeqz.domain.repository.UserPreferencesRepository
import com.example.blazeqz.domain.util.onFailure
import com.example.blazeqz.domain.util.onSuccess
import com.example.blazeqz.presentation.navigation.Route
import com.example.blazeqz.presentation.util.getErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(
    private val questionRepository: QuizQuestionRepository,
    private val topicRepository: QuizTopicRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val topicCode = savedStateHandle.toRoute<Route.QuizScreen>().topicCode

    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private val _event = Channel<QuizEvent>()
    val event = _event.receiveAsFlow()

    init {
        setupQuiz()
    }

    fun onAction(action: QuizAction) {
        when (action) {
            QuizAction.NextButtonClick -> {
                val newIndex =
                    (state.value.currentQuestionIndex + 1).coerceAtMost(state.value.questions.lastIndex)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            QuizAction.PreviousButtonClick -> {
                val newIndex = (state.value.currentQuestionIndex - 1).coerceAtLeast(0)
                _state.update { it.copy(currentQuestionIndex = newIndex) }
            }

            is QuizAction.JumpToQuestion -> {
                _state.update { it.copy(currentQuestionIndex = action.index) }
            }

            is QuizAction.OnOptionSelected -> {
                val newAnswer = UserAnswer(action.questionId, action.answer)
                val currentAnswers = state.value.answers.toMutableList()
                val existingAnswerIndex =
                    currentAnswers.indexOfFirst { it.questionId == action.questionId }
                if (existingAnswerIndex != -1) {
                    currentAnswers[existingAnswerIndex] = newAnswer
                } else {
                    currentAnswers.add(newAnswer)
                }
                _state.update { it.copy(answers = currentAnswers) }
            }

            QuizAction.ExitQuizButtonClick -> {
                _state.update { it.copy(isExitQuizDialogOpen = true) }
            }

            QuizAction.ExitQuizConfirmButtonClick -> {
                _state.update { it.copy(isExitQuizDialogOpen = false) }
                _event.trySend(QuizEvent.NavigateToDashboardScreen)
            }

            QuizAction.ExitQuizDialogDismiss -> {
                _state.update { it.copy(isExitQuizDialogOpen = false) }
            }

            QuizAction.Refresh -> {
                setupQuiz()
            }

            QuizAction.SubmitQuizButtonClick -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = true) }
            }

            QuizAction.SubmitQuizConfirmButtonClick -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = false) }
                submitQuiz()
            }

            QuizAction.SubmitQuizDialogDismiss -> {
                _state.update { it.copy(isSubmitQuizDialogOpen = false) }
            }
        }
    }

    private fun setupQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, loadingMessage = "Setting up the Quiz..."
                )
            }
            getQuizTopicName(topicCode)
            getQuizQuestions(topicCode)
            _state.update {
                it.copy(
                    isLoading = false, loadingMessage = null
                )
            }
        }
    }

    private suspend fun getQuizQuestions(topicCode: Int) {
        questionRepository.fetchAndSaveQuizQuestions(topicCode).onSuccess { questions ->
            _state.update {
                it.copy(
                    questions = questions,
                    errorMessage = null
                )
            }
        }.onFailure { error ->
            _state.update {
                it.copy(
                    questions = emptyList(),
                    errorMessage = error.getErrorMessage()
                )
            }
        }
    }

    private suspend fun getQuizTopicName(topicCode: Int) {
        topicRepository.getQuizTopicByCode(topicCode)
            .onSuccess { topic ->
                _state.update {
                    it.copy(
                        topBarTitle = topic.name
                    )
                }
            }
            .onFailure { error ->
                _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
            }
    }

    private fun submitQuiz() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, loadingMessage = "Submitting the Quiz..."
                )
            }
            saveUserAnswers()
            updateScore()
            _state.update {
                it.copy(
                    isLoading = false, loadingMessage = null
                )
            }
            _event.send(QuizEvent.NavigateToResultScreen)
        }
    }


    private suspend fun saveUserAnswers() {
        questionRepository.saveUserAnswers(state.value.answers)
            .onFailure { error ->
                _event.send(QuizEvent.ShowToast(error.getErrorMessage()))
            }
    }

    private suspend fun updateScore() {
        val quizQuestions = state.value.questions
        val userAnswers = state.value.answers

        val correctAnswersCount = userAnswers.count { answer ->
            val question = quizQuestions.find { it.id == answer.questionId }
            question?.correctAnswer == answer.selectedOption
        }

        val previousAttempted = userPreferencesRepository.getQuestionAttempted().first()
        val previousCorrect = userPreferencesRepository.getCorrectAnswers().first()

        val totalAttempted = previousAttempted + userAnswers.size
        val totalCorrect = previousCorrect + correctAnswersCount

        userPreferencesRepository.saveScore(
            questionAttempted = totalAttempted,
            correctAnswers = totalCorrect
        )
    }

}