package sample.jpa.comment.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.comment.model.entitiy.Comment

@Repository
interface CommentRepository:JpaRepository<Comment, Long> {
}