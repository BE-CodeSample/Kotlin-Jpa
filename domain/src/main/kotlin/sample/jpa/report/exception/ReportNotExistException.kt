package sample.jpa.report.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ReportNotExistException : RuntimeException("존재하지 않는 신고입니다."){
}