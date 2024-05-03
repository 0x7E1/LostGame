package tech.lostgame.test.repository

import org.springframework.stereotype.Component
import tech.lostgame.test.entity.Transaction
import tech.lostgame.test.entity.User

@Component
class GamesProcessorRepository(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val transactionRepository: TransactionRepository
) {

    fun createTransaction(transaction: Transaction): Transaction {
        return transactionRepository.saveAndFlush(transaction)
    }

    fun updateUsersBalance(userId: String, amount: Long): Long {
        val user = userRepository.findById(userId).get()
        user.balance += amount

        return userRepository.saveAndFlush(user).balance
    }

    fun getUserBySessionId(gameSessionId: String): User {
        return sessionRepository
            .findById(gameSessionId)
            .get()
            .user
    }

    fun completeRound() {

    }
}