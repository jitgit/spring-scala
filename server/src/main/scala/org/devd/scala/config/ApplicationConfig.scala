package org.devd.scala.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.{Bean, Configuration}


@Configuration
class ApplicationConfig {
  @Bean
  def objectMapper: ObjectMapper = {
    new ScalaObjectMapper()
  }
}
