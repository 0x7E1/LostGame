package tech.lostgame.test.service

import org.springframework.stereotype.Service
import tech.lostgame.test.dto.ContentDTO
import tech.lostgame.test.dto.request.RequestDTO
import tech.lostgame.test.dto.response.ResponseDTO
import tech.lostgame.test.entity.Transaction
import tech.lostgame.test.entity.enums.Errors
import tech.lostgame.test.entity.enums.OperationType
import tech.lostgame.test.entity.enums.TransactionStatus
import tech.lostgame.test.repository.GamesProcessorRepository

@Service
class GamesProcessorService(val repository: GamesProcessorRepository) {

    fun handleRequest(request: RequestDTO): ResponseDTO {
        return try {
            when (OperationType.valueOf(request.api.uppercase())) {
                OperationType.BALANCE -> processBalanceRequest(request)
                OperationType.DEBIT, OperationType.CREDIT -> processDebitCreditRequest(request)
                OperationType.ROLLBACK -> processRollbackRequest(request)
                OperationType.META_DATA -> processMetadataRequest(request)
            }
        } catch (e: Exception) {
            ResponseDTO(
                request.api,
                false,
                "Unknown API operation type",
                Errors.INTERNAL_ERROR.toString()
            )
        }
    }

    private fun processBalanceRequest(request: RequestDTO): ResponseDTO {
        val user = repository.getUserBySessionId(request.data.gameSessionId!!)

        return if (user.currencies.contains(request.data.currency)) {
            ResponseDTO(
                request.api,
                data = ContentDTO(
                    userNick = user.username,
                    amount = user.balance,
                    denomination = user.denomination,
                    maxWin = user.maxWin,
                    currency = request.data.currency,
                    userId = user.userId,
                    jpKey = user.jpKey
                )
            )
        } else {
            ResponseDTO(
                request.api,
                false,
                "The user doesn't have such currency",
                Errors.UNKNOWN_CURRENCY.toString()
            )
        }
    }

    private fun processDebitCreditRequest(request: RequestDTO): ResponseDTO {
        val user = repository.getUserBySessionId(request.data.gameSessionId!!)

        val transaction = repository.createTransaction(
            Transaction(
                id = null,
                TransactionStatus.NOT_PROCESSED,
                request.data.gameSessionId!!,
                request.data.transactionId!!,
                request.data.amount!!,
                request.data.currency!!,
                request.data.betId!!,
                user.userId
            )
        )
        val currentBalance = repository.updateUsersBalance(user.userId, transaction.amount)

        return ResponseDTO(
            request.api,
            true,
            "",
            Errors.NO_ERRORS.toString(),
            ContentDTO(
                transactionId = transaction.transactionId,
                userNick = user.username,
                amount = currentBalance,
                denomination = user.denomination,
                maxWin = user.maxWin,
                currency = request.data.currency
            )
        )
    }

    private fun processRollbackRequest(request: RequestDTO): ResponseDTO {
        // NOT IMPLEMENTED YET
        return ResponseDTO(
            request.api,
            false,
            "Rollback not implemented yet",
            Errors.INTERNAL_ERROR.toString()
        )
    }

    private fun processMetadataRequest(request: RequestDTO): ResponseDTO {
        return ResponseDTO(
            request.api,
            true,
            "",
            Errors.NO_ERRORS.toString(),
            ContentDTO(
                api = "roundComplete"
            )
        )
    }
}