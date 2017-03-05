import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = Array("org.devd.scala"))
class Application


object ApplicationBoot extends App {
  SpringApplication.run(classOf[Application])
}