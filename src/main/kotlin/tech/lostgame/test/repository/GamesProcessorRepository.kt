package tech.lostgame.test.repository

import org.springframework.stereotype.Component
import tech.lostgame.test.entity.Transaction
import tech.lostgame.test.entity.User
import tech.lostgame.test.entity.enums.TransactionStatus
import tech.lostgame.test.exception.AlreadyProcessedTransactionException

@Component
class GamesProcessorRepository(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val transactionRepository: TransactionRepository
) {

    fun createTransaction(apiType: String, transaction: Transaction): Transaction {
        transactionRepository.findById(transaction.transactionId)
            .ifPresent {
                throw AlreadyProcessedTransactionException(apiType, "The transaction has already been successfully processed")
            }

        return transactionRepository.saveAndFlush(transaction)
    }

    fun completeTransaction(apiType: String, transactionId: String) {
        val existed = transactionRepository.findById(transactionId).get()

        if (existed.status != TransactionStatus.COMPLETED) {
            existed.status = TransactionStatus.COMPLETED

            transactionRepository.saveAndFlush(existed)
        } else {
            throw AlreadyProcessedTransactionException(apiType, "The transaction has already been successfully processed")
        }
    }

    fun updateUsersBalance(userId: String, amount: Long): Long {
        val user = userRepository.findById(userId).get()
        user.balance += amount

        val updated = userRepository.saveAndFlush(user)

        return updated.balance
    }

    fun getUserBySessionId(gameSessionId: String): User {
        return userRepository.findBySessionId(gameSessionId)
    }

    fun completeRound(betId: String) {
        println("Round \"$betId\" completed!")
    }
}