package sample.jpa.advertisement.model.repository

import org.springframework.data.jpa.repository.JpaRepository
import sample.jpa.advertisement.model.entity.Advertisement

interface AdvertisementRepository: JpaRepository<Advertisement, Long> {
}