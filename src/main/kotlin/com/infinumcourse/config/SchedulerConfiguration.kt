package com.infinumcourse.config

import com.infinumcourse.APIInfo.service.RestTemplateCarService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
@EnableScheduling
class SchedulerConfiguration(private val rtCarService: RestTemplateCarService): SchedulingConfigurer {
    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        taskRegistrar.addFixedDelayTask({rtCarService.getManufacturersAndModels()}, 2000)
    }
}