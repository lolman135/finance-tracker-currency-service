package tracker.currencyservice.infrasctructure.mapper.jpa

import tracker.currencyservice.domain.model.CurrencyCode
import tracker.currencyservice.domain.model.ExchangeRate
import tracker.currencyservice.persistence.ExchangeRateEntity
import org.springframework.stereotype.Component

@Component
class ExchangeRatesJpaDomainMapperImpl : ExchangeRatesJpaDomainMapper {

    override fun toDomain(entity: ExchangeRateEntity): ExchangeRate {
        return ExchangeRate(
            from = CurrencyCode.valueOf(entity.from),
            to = CurrencyCode.valueOf(entity.to),
            rate = entity.rate,
            fetchedAt = entity.fetchedAt
        )
    }

    override fun toEntity(domain: ExchangeRate): ExchangeRateEntity {
        return ExchangeRateEntity(
            from = domain.from.name,
            to = domain.to.name,
            rate = domain.rate,
            fetchedAt = domain.fetchedAt
        )
    }
}