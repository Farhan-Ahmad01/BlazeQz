package com.example.blazeqz.presentation.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blazeqz.domain.repository.QuestionsCountRepository
import com.example.blazeqz.domain.repository.QuizTopicRepository
import com.example.blazeqz.domain.repository.UserPreferencesRepository
import com.example.blazeqz.domain.util.onFailure
import com.example.blazeqz.domain.util.onSuccess
import com.example.blazeqz.presentation.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val topicRepository: QuizTopicRepository,
    private val questionsCountRepository: QuestionsCountRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        userPreferencesRepository.getQuestionAttempted(),
        userPreferencesRepository.getCorrectAnswers(),
        userPreferencesRepository.getUsername()
    ) { state, questionAttempted, correctAnswers, username ->
        state.copy(
            questionAttempted = questionAttempted,
            correctAnswer = correctAnswers,
            username = username
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _state.value
    )

    init {
        getQuizTopics()
        Log.d("QuestionsCountCheck", "ViewModel Questions Count method called")
        getQuestionsCount()
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.NameEditDialogConfirm -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
                saveUsername(state.value.usernameTextFieldValue)
            }

            DashboardAction.NameEditIconClick -> {
                _state.update {
                    it.copy(
                        usernameTextFieldValue = state.value.username,
                        isNameEditDialogOpen = true
                    )
                }
            }

            DashboardAction.NameEditIconDismiss -> {
                _state.update { it.copy(isNameEditDialogOpen = false) }
            }

            is DashboardAction.SetUsername -> {
                val usernameError = validateUsername(action.username)
                _state.update {
                    it.copy(
                        usernameTextFieldValue = action.username,
                        usernameError = usernameError
                    )
                }
            }

            DashboardAction.RefreshIconClick -> {
                getQuizTopics()
            }
        }
    }

    private fun getQuestionsCount() {Log.d("QuestionsCountCheck", "ViewModel Questions Count method initiated")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            questionsCountRepository.getQuestionsCount()
                .onSuccess { questionsCount ->
                    Log.d("QuestionsCountCheck", "Questions Count received, count: ${questionsCount.count}")
                    _state.update {
                        it.copy(
                            allQuestionsCount = questionsCount.count,
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    Log.d("QuestionsCountCheck", "Failed to get questions Count, ${error.getErrorMessage()}")
                    _state.update {
                        it.copy(
                            errorMessage = error.getErrorMessage(),
                            isLoading = false
                        )
                    }
                }
        }
    }


    private fun getQuizTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            topicRepository.getQuizTopics()
                .onSuccess { topics ->
                    _state.update {
                        it.copy(
                            quizTopics = topics,
                            errorMessage = null,
                            isLoading = false,
                        )
                    }
                }
                .onFailure { error ->

                    _state.update {
                        it.copy(
                            quizTopics = emptyList(),
                            errorMessage = error.getErrorMessage(),
                            isLoading = false
                        )
                    }
                }
        }

    }

    private fun saveUsername(username: String) {
        viewModelScope.launch {
            val trimmedUsername = username.trim()
            userPreferencesRepository.saveUsername(trimmedUsername)
        }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isBlank() -> "Please enter your name"
            username.length < 3 -> "Name is too short"
            username.length > 20 -> "Name is too long"
            else -> null
        }
    }

}