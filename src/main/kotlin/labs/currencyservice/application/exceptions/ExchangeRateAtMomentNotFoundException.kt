package labs.currencyservice.application.exceptions

import labs.currencyservice.domain.model.CurrencyCode
import java.time.Instant

class ExchangeRateAtMomentNotFoundException(from: CurrencyCode, to: CurrencyCode, before: Instant)
    : DomainNotFoundException("$from to $to exchange rate before $before not found"){
}