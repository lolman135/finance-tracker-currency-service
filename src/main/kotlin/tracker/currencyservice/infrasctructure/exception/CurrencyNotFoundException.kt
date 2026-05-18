package tracker.currencyservice.infrasctructure.exception

class CurrencyNotFoundException(val invalidCurrencyCode: String)
    : Exception("Currency $invalidCurrencyCode does not supported"){
}