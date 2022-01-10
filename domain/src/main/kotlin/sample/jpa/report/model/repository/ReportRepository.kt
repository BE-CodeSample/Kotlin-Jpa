package sample.jpa.report.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.report.model.entity.Report

@Repository
interface ReportRepository : JpaRepository<Report, Long> {
}