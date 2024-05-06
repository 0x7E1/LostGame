package tech.lostgame.test.service

import org.springframework.stereotype.Service
import tech.lostgame.test.dto.request.RequestDTO
import tech.lostgame.test.entity.enums.OperationType
import tech.lostgame.test.exception.ValidationException

@Service
class ValidationService {

    fun validateRequest(request: RequestDTO) {
        try {
            when (OperationType.valueOf(request.api.uppercase())) {
                OperationType.BALANCE -> validateBalanceContent(request)
                OperationType.DEBIT, OperationType.CREDIT -> validateDebitCreditContent(request)
                OperationType.ROLLBACK -> validateRollbackContent(request)
                OperationType.METADATA -> validateMetadataContent(request)
            }
        } catch (e: Exception) {
            throw UnsupportedOperationException(String.format("Unknown API operation type: %s", request.api))
        }
    }

    fun validateBalanceContent(request: RequestDTO) {
        if (request.data.gameSessionId.isNullOrBlank() || request.data.currency.isNullOrBlank()) {
            throw ValidationException(request.api)
        }
    }

    fun validateDebitCreditContent(request: RequestDTO) {
        if (
            request.data.transactionId.isNullOrBlank()
            || request.data.gameSessionId.isNullOrBlank()
            || request.data.currency.isNullOrBlank()
            || request.data.betId.isNullOrBlank()
            || request.data.amount == null
        ) {
            throw ValidationException(request.api)
        }
    }

    fun validateRollbackContent(request: RequestDTO) {
        TODO("Not implemented yet")
    }

    fun validateMetadataContent(request: RequestDTO) {
        if (
            request.data.gameSessionId.isNullOrBlank()
            || request.data.currency.isNullOrBlank()
            || !request.data.api.equals("roundComplete", true)
            || request.data.metadata?.betId.isNullOrBlank()
        ) {
            throw ValidationException(request.api)
        }
    }
}
