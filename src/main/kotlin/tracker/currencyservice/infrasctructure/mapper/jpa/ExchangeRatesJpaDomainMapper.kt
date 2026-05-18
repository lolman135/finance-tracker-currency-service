package tracker.currencyservice.infrasctructure.mapper.jpa

import tracker.currencyservice.domain.model.ExchangeRate
import tracker.currencyservice.persistence.ExchangeRateEntity

interface ExchangeRatesJpaDomainMapper {

    fun toDomain(entity: ExchangeRateEntity): ExchangeRate
    fun toEntity(domain: ExchangeRate): ExchangeRateEntity
}