package tech.lostgame.test.exception

class UnknownUserCurrencyException(val api: String, message: String) : IllegalStateException(message) {
}
