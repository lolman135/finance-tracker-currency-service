package labs.currencyservice.persistence.jpa

import labs.currencyservice.domain.ExchangeRateRepository
import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import labs.currencyservice.infrasctructure.mapper.jpa.ExchangeRatesJpaDomainMapper
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

    override fun findLatestRateBefore(
        from: CurrencyCode,
        to: CurrencyCode,
        timestamp: Instant
    ): ExchangeRate? {
        return jpaRepository.findLatestRateBefore(from.toString(), to.toString(), timestamp)
            .map {mapper.toDomain(it)}
            .getOrNull()
    }

}