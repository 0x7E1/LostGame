package tech.lostgame.test.exception

class AlreadyProcessedTransactionException(val api: String, message: String) : IllegalStateException(message) {
}
