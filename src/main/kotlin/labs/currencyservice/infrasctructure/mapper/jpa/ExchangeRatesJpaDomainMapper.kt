package labs.currencyservice.infrasctructure.mapper.jpa

import labs.currencyservice.domain.model.ExchangeRate
import labs.currencyservice.persistence.ExchangeRateEntity

interface ExchangeRatesJpaDomainMapper {

    fun toDomain(entity: ExchangeRateEntity): ExchangeRate
    fun toEntity(domain: ExchangeRate): ExchangeRateEntity
}