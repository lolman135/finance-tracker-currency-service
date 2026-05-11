package labs.currencyservice.application.exceptions

open class DomainNotFoundException(override val message: String?) : Exception(message) {
}