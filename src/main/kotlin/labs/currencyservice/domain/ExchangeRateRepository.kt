package labs.currencyservice.domain

import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import java.time.Instant

interface ExchangeRateRepository {
    fun findLatestRate(from: CurrencyCode, to: CurrencyCode): ExchangeRate?
    fun save(rate: ExchangeRate): ExchangeRate

    fun findLatestRateBefore(
        from: CurrencyCode,
        to: CurrencyCode,
        timestamp: Instant
    ): ExchangeRate?
}