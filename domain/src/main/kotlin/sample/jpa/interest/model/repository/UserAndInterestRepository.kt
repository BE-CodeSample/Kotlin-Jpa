package sample.jpa.interest.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import sample.jpa.interest.model.entity.UserInterest


interface UserAndInterestRepository:JpaRepository<UserInterest,Long> {
}