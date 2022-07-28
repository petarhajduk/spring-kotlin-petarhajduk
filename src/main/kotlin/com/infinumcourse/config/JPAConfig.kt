package com.infinumcourse.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@Profile("h2")
class JPAConfig {

    @Bean
    fun datasource(): DataSource {
        DataSourceBuilder.create()
        val ds = DriverManagerDataSource()
        ds.url = "jdbc:h2:file:/tmp/myH2"
        ds.setDriverClassName("org.h2.Driver")
        ds.username = "admin"
        ds.password = "12345678"
        return ds
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}