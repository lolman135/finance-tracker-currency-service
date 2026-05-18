package tracker.currencyservice.domain

import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.domain.model.ExchangeRate
import java.time.Instant

interface ExchangeRateRepository {
    fun findLatestRate(from: CurrencyCode, to: CurrencyCode): ExchangeRate?
    fun save(rate: ExchangeRate): ExchangeRate
    fun saveAll(rates: List<ExchangeRate>)
    fun findLatestRates(from: CurrencyCode, toCurrencies: List<CurrencyCode>): List<ExchangeRate>

    fun findLatestRatesBefore(
        from: CurrencyCode,
        toCurrencies: List<CurrencyCode>,
        fetchedBefore: Instant
    ): List<ExchangeRate>

    fun findLatestRateBefore(
        from: CurrencyCode,
        to: CurrencyCode,
        fetchedBefore: Instant
    ): ExchangeRate?
}