package tracker.currencyservice.application.exception

open class DomainNotFoundException(override val message: String?) : Exception(message) {
}