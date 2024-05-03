package tech.lostgame.test.entity

import jakarta.persistence.Entity
import tech.lostgame.test.entity.enums.TransactionStatus

@Entity
data class Transaction(
    var id: Long?,
    var status: TransactionStatus,
    var gameSessionId: String,
    var transactionId: String,
    var amount: Long,
    var currency: String,
    var betId: String,
    var userId: String
)
