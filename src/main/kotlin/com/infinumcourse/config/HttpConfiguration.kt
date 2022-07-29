package com.infinumcourse.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class HttpConfiguration {

    @Bean
    fun provideRestTemplate(
        restTemplateBuilder: RestTemplateBuilder,
        @Value("\${carcheckup-service.base-url}") baseUrl: String
    ): RestTemplate{
        return restTemplateBuilder.rootUri(baseUrl).build()
    }

    @Bean
    fun provideWebClient(
        webClientBuilder: WebClient.Builder,
        @Value("\${carcheckup-service.base-url}") baseUrl: String
    ): WebClient{
        return webClientBuilder.baseUrl(baseUrl).build()
    }
}