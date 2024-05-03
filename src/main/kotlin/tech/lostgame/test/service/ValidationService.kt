package tech.lostgame.test.service

import org.springframework.stereotype.Service
import tech.lostgame.test.dto.ContentDTO
import tech.lostgame.test.dto.request.RequestDTO
import tech.lostgame.test.entity.enums.OperationType

@Service
class ValidationService {

    fun validateRequest(request: RequestDTO) {
        when (OperationType.valueOf(request.api.uppercase())) {
            OperationType.BALANCE -> validateBalance(request.data)
            OperationType.DEBIT, OperationType.CREDIT -> validateDebitCredit(request.data)
            OperationType.ROLLBACK -> validateRollback()
            OperationType.META_DATA -> validateMetadata(request.data)
        }
    }

    fun validateBalance(data: ContentDTO) {

    }

    fun validateDebitCredit(data: ContentDTO) {

    }

    fun validateRollback() {
        TODO("Not implemented yet")
    }

    fun validateMetadata(data: ContentDTO) {

    }
}