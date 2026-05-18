package tracker.currencyservice.application.exception

import tracker.currencyservice.domain.model.CurrencyCode

class ExchangeRateNotFoundException(from: CurrencyCode, to: CurrencyCode) :
    DomainNotFoundException("$from to $to exchange rate not found")