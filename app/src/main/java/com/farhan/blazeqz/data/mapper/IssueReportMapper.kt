package com.farhan.blazeqz.data.mapper

import com.farhan.blazeqz.data.remote.dto.IssueReportDto
import com.farhan.blazeqz.data.util.toFormattedDateTimeString
import com.farhan.blazeqz.domain.model.IssueReport

fun IssueReport.toIssueReportDto() = IssueReportDto(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timeStampMillis.toFormattedDateTimeString()
)