package tech.lostgame.test.service

import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Service
import tech.lostgame.test.dto.ContentDTO
import tech.lostgame.test.dto.request.RequestDTO
import tech.lostgame.test.dto.response.ResponseDTO
import tech.lostgame.test.entity.Transaction
import tech.lostgame.test.entity.User
import tech.lostgame.test.entity.enums.Errors
import tech.lostgame.test.entity.enums.OperationType
import tech.lostgame.test.entity.enums.TransactionStatus
import tech.lostgame.test.exception.UnknownUserCurrencyException
import tech.lostgame.test.repository.GamesProcessorRepository

@Service
class GamesProcessorService(private val repository: GamesProcessorRepository) {

    fun handleRequest(request: RequestDTO): ResponseDTO {
        return when (OperationType.valueOf(request.api.uppercase())) {
            OperationType.BALANCE -> processBalanceRequest(request)
            OperationType.DEBIT -> processDebitRequest(request)
            OperationType.CREDIT -> processCreditRequest(request)
            OperationType.ROLLBACK -> processRollbackRequest(request)
            OperationType.METADATA -> processMetadataRequest(request)
        }
    }

    private fun processBalanceRequest(request: RequestDTO): ResponseDTO {
        val user = repository.getUserBySessionId(request.data.gameSessionId!!)
        checkCurrency(user, request)

        return ResponseDTO(
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
    }

    private fun processDebitRequest(request: RequestDTO): ResponseDTO {
        val user = repository.getUserBySessionId(request.data.gameSessionId!!)
        checkCurrency(user, request)

        val transaction = repository.createTransaction(
            request.api,
            Transaction(
                TransactionStatus.NOT_COMPLETED,
                request.data.gameSessionId!!,
                request.data.transactionId!!,
                request.data.amount!!,
                request.data.currency!!,
                request.data.betId!!,
                user.userId
            )
        )

        return ResponseDTO(
            request.api,
            true,
            Strings.EMPTY,
            Errors.NO_ERRORS.toString(),
            ContentDTO(
                transactionId = transaction.transactionId,
                userNick = user.username,
                amount = user.balance,
                denomination = user.denomination,
                maxWin = user.maxWin,
                currency = request.data.currency
            )
        )
    }

    private fun processCreditRequest(request: RequestDTO): ResponseDTO {
        val user = repository.getUserBySessionId(request.data.gameSessionId!!)
        checkCurrency(user, request)

        repository.completeTransaction(request.api, request.data.transactionId!!)
        val actualBalance = repository.updateUsersBalance(user.userId, request.data.amount!!)

        return ResponseDTO(
            request.api,
            true,
            Strings.EMPTY,
            Errors.NO_ERRORS.toString(),
            ContentDTO(
                transactionId = request.data.transactionId,
                userNick = user.username,
                amount = actualBalance,
                denomination = user.denomination,
                maxWin = user.maxWin,
                currency = request.data.currency
            )
        )
    }

    private fun processRollbackRequest(request: RequestDTO): ResponseDTO {
        TODO("Not implemented yet")
    }

    private fun processMetadataRequest(request: RequestDTO): ResponseDTO {
        repository.completeRound(request.data.betId!!)

        return ResponseDTO(
            request.api,
            true,
            Strings.EMPTY,
            Errors.NO_ERRORS.toString(),
            ContentDTO(
                api = "roundComplete"
            )
        )
    }

    private fun checkCurrency(user: User, request: RequestDTO) {
        if (!user.currencies.contains(request.data.currency)) {
            throw UnknownUserCurrencyException(request.api, "The user doesn't have such currency")
        }
    }
}
