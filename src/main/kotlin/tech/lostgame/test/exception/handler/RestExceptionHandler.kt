package tech.lostgame.test.exception.handler

import org.apache.logging.log4j.util.Strings
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech.lostgame.test.dto.response.ResponseDTO
import tech.lostgame.test.entity.enums.Errors
import tech.lostgame.test.exception.AlreadyProcessedTransactionException
import tech.lostgame.test.exception.InsufficientUserBalanceException
import tech.lostgame.test.exception.UnknownUserCurrencyException
import tech.lostgame.test.exception.ValidationException

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler
    fun handleGeneralException(e: Exception): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, Strings.EMPTY, e.message.toString(), Errors.INTERNAL_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler
    fun handleValidationException(e: ValidationException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, e.api, e.message.toString(), Errors.INVALID_BODY),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler
    fun handleUnsupportedApiOperationException(e: UnsupportedOperationException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, Strings.EMPTY, e.message.toString(), Errors.INVALID_BODY),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler
    fun handleInsufficientUsersBalance(e: InsufficientUserBalanceException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, e.api, e.message.toString(), Errors.INSUFFICIENT_BALANCE),
            HttpStatus.EXPECTATION_FAILED
        )
    }

    @ExceptionHandler
    fun handleUnknownUsersCurrency(e: UnknownUserCurrencyException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, e.api, e.message.toString(), Errors.UNKNOWN_CURRENCY),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler
    fun handleNotFoundException(e: NoSuchElementException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(false, Strings.EMPTY, e.message.toString(), null),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler
    fun handleAlreadyProcessedTransactionException(e: AlreadyProcessedTransactionException): ResponseEntity<ResponseDTO> {
        return ResponseEntity(
            buildResponse(true, e.api, e.message.toString(), Errors.ALREADY_PROCESSED),
            HttpStatus.ALREADY_REPORTED
        )
    }

    private fun buildResponse(isSuccess: Boolean, api: String, message: String, errorType: Errors?): ResponseDTO {
        return ResponseDTO(api, isSuccess, message, errorType.toString())
    }
}
