package com.example.blazeqz.domain.repository

import com.example.blazeqz.domain.model.IssueReport
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

interface IssueReportRepository {

    suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError>

}