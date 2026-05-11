package labs.currencyservice.application.usecase

interface UseCase<I, O> {
    fun execute(command: I): O
}