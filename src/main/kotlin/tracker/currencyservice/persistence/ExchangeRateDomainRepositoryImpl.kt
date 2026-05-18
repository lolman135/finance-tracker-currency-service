package tracker.currencyservice.persistence

import jakarta.transaction.Transactional
import tracker.currencyservice.domain.ExchangeRateRepository
import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.domain.model.ExchangeRate
import tracker.currencyservice.infrasctructure.mapper.jpa.ExchangeRatesJpaDomainMapper
import org.springframework.stereotype.Repository
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

@Repository
class ExchangeRateDomainRepositoryImpl(
    private val mapper: ExchangeRatesJpaDomainMapper,
    private val jpaRepository: ExchangeRateJpaRepository
) : ExchangeRateRepository {

    override fun findLatestRate(from: CurrencyCode, to: CurrencyCode): ExchangeRate? =
         jpaRepository.findLatestRate(from.toString(), to.toString())
            .map { mapper.toDomain(it) }
            .getOrNull()

    override fun save(rate: ExchangeRate): ExchangeRate {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(rate)))
    }

    @Transactional
    override fun saveAll(rates: List<ExchangeRate>) {
        val entities = rates.map { mapper.toEntity(it) }
        jpaRepository.saveAll(entities)
    }

    override fun findLatestRates(from: CurrencyCode, toCurrencies: List<CurrencyCode>): List<ExchangeRate> {
        val codesAsStrings = toCurrencies.map { it.name }
        val exchangeRates = jpaRepository.findLatestRates(from.name, codesAsStrings).map { mapper.toDomain(it) }
        return exchangeRates
    }

    override fun findLatestRatesBefore(
        from: CurrencyCode,
        toCurrencies: List<CurrencyCode>,
        fetchedBefore: Instant
    ): List<ExchangeRate> {
        val codesAsStrings = toCurrencies.map { it.name }
        val exchangeRates = jpaRepository.findLatestRatesBefore(from.name, codesAsStrings, fetchedBefore).map { mapper.toDomain(it) }
        return exchangeRates
    }

    override fun findLatestRateBefore(
        from: CurrencyCode,
        to: CurrencyCode,
        fetchedBefore: Instant
    ): ExchangeRate? {
        return jpaRepository.findLatestRateBefore(from.toString(), to.toString(), fetchedBefore)
            .map {mapper.toDomain(it)}
            .getOrNull()
    }

}