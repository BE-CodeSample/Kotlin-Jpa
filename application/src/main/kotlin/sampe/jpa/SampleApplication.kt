package sampe.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
open class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}