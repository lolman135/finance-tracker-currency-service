package labs.currencyservice.infrasctructure.mapper.jpa

import labs.currencyservice.domain.model.CurrencyCode
import labs.currencyservice.domain.model.ExchangeRate
import labs.currencyservice.persistence.jpa.ExchangeRateEntity
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
            from = domain.from.toString(),
            to = domain.to.toString(),
            rate = domain.rate,
            fetchedAt = domain.fetchedAt
        )
    }
}