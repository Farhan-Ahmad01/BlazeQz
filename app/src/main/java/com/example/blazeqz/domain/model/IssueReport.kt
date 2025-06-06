package com.example.blazeqz.domain.model

data class IssueReport(
    val questionId: String,
    val issueType: String,
    val additionalComment: String?,
    val userEmail: String?,
    val timeStampMillis: Long
)
