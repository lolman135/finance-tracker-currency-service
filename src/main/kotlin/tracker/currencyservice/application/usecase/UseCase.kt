package tracker.currencyservice.application.usecase

interface UseCase<I, O> {
    fun execute(command: I): O
}