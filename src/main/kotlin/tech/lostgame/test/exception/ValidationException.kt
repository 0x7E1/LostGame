package tech.lostgame.test.exception

class ValidationException(val api: String) : IllegalStateException("Invalid request body") {
}
