package com.example.blazeqz.data.mapper

import com.example.blazeqz.data.remote.dto.IssueReportDto
import com.example.blazeqz.data.util.toFormattedDateTimeString
import com.example.blazeqz.domain.model.IssueReport

fun IssueReport.toIssueReportDto() = IssueReportDto(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timeStampMillis.toFormattedDateTimeString()
)