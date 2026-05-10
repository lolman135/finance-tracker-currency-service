package labs.currencyservice

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<CurrencyServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
