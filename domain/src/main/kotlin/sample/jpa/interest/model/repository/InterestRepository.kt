package sample.jpa.interest.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import sample.jpa.interest.model.entity.Interest

interface InterestRepository:JpaRepository<Interest, Long> {
}