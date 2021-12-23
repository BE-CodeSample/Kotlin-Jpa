package sample.jpa.post.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sample.jpa.post.model.entity.Post

interface PostRepository:JpaRepository<Post, Long> {
}