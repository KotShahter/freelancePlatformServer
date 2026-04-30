package com.withABow.freelancePlatform

import com.withABow.freelancePlatform.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface Repository : JpaRepository <User, Int> {

}