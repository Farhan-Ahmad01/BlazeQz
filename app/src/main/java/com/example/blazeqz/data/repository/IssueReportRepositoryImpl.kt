package com.example.blazeqz.data.repository

import com.example.blazeqz.data.mapper.toIssueReportDto
import com.example.blazeqz.data.remote.RemoteQuizDataSource
import com.example.blazeqz.domain.model.IssueReport
import com.example.blazeqz.domain.repository.IssueReportRepository
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

class IssueReportRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource
): IssueReportRepository {

    override suspend fun insertIssueReport(
        report: IssueReport
    ): Result<Unit, DataError> {
        val reportDto = report.toIssueReportDto()
        return remoteDataSource.insertIssueReport(reportDto)
    }
}