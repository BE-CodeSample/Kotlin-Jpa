package sample.jpa.file.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import sample.jpa.file.model.entity.File


interface FileRepository: JpaRepository<File, Long> {
}