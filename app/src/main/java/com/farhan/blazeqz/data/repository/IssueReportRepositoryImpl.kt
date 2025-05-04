package com.farhan.blazeqz.data.repository

import com.farhan.blazeqz.data.mapper.toIssueReportDto
import com.farhan.blazeqz.data.remote.RemoteQuizDataSource
import com.farhan.blazeqz.domain.model.IssueReport
import com.farhan.blazeqz.domain.repository.IssueReportRepository
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

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