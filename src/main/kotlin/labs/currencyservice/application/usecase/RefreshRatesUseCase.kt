package labs.currencyservice.application.usecase

import labs.currencyservice.application.external.ExchangeRateCache
import labs.currencyservice.application.external.ExternalRateProvider
import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class RefreshRatesUseCase(
    private val exchangeRateCache: ExchangeRateCache,
    private val exchangeRateRepository: ExchangeRateRepository,
    private val externalRateProvider: ExternalRateProvider
) : UseCase<Unit, Unit>{

    override fun execute(command: Unit) {

        val allRatesMap = externalRateProvider.getAllRates(CurrencyCode.USD)
        println(allRatesMap)

        val rates = allRatesMap.map { (currency, value) ->
            ExchangeRate(
                from = CurrencyCode.USD,
                to = currency,
                rate = value,
                fetchedAt = Instant.now()
            )
        }

        exchangeRateRepository.saveAll(rates)

        rates.forEach {
            exchangeRateCache.saveRate(it.from, it.to, it.rate)
        }

    }
}