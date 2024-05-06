package tech.lostgame.test.exception

class InsufficientUserBalanceException(val api: String, message: String) : IllegalStateException(message) {
}
