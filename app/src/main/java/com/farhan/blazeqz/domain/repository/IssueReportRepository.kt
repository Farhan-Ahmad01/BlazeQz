package com.farhan.blazeqz.domain.repository

import com.farhan.blazeqz.domain.model.IssueReport
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

interface IssueReportRepository {

    suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError>

}