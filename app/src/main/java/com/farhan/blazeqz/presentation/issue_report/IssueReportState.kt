package com.farhan.blazeqz.presentation.issue_report

import com.farhan.blazeqz.domain.model.QuizQuestion

data class IssueReportState(
    val quizQuestion: QuizQuestion? = null,
    val isQuestionCardExpanded: Boolean = false,
    val issueType: IssueType = IssueType.OTHER,
    val otherIssueText: String = "",
    val additionalComment: String = "",
    val emailForFollowUp: String = ""
)

enum class IssueType(val text: String) {
    INCORRECT_ANSWER(text = "Incorrect Answer"),
    UNCLEAR_QUESTION(text = "Question is Unclear"),
    TYPO_OR_GRAMMAR(text = "Typo or Grammar Mistake"),
    OTHER(text = "Other"),
}
