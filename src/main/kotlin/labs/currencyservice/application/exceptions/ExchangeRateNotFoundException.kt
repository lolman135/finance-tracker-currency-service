package labs.currencyservice.application.exceptions

import labs.currencyservice.domain.model.CurrencyCode

class ExchangeRateNotFoundException(from: CurrencyCode, to: CurrencyCode) :
    DomainNotFoundException("$from to $to exchange rate not found")