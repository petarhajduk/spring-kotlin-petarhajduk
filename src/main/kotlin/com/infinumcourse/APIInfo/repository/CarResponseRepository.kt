package com.infinumcourse.APIInfo.repository

import com.infinumcourse.APIInfo.entities.CarResponse
import com.infinumcourse.APIInfo.entities.FormatForDataBase
import org.springframework.data.repository.Repository
import java.util.*

interface CarResponseRepository: Repository<CarResponse, UUID> {
    fun saveAll(formatForDataBase: Iterable<FormatForDataBase>): Iterable<FormatForDataBase>

    fun findAll(): Iterable<FormatForDataBase>
}