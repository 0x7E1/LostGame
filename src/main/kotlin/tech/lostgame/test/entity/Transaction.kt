package tech.lostgame.test.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import tech.lostgame.test.entity.enums.TransactionStatus

@Entity(name = "transactions")
data class Transaction(
    var status: TransactionStatus,
    var gameSessionId: String,
    @Id
    var transactionId: String,
    var amount: Long,
    var currency: String,
    var betId: String,
    var userId: String
)
