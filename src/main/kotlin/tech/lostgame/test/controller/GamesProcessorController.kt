package tech.lostgame.test.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.lostgame.test.dto.request.RequestDTO
import tech.lostgame.test.dto.response.ResponseDTO
import tech.lostgame.test.service.GamesProcessorService
import tech.lostgame.test.service.ValidationService

@RestController
@RequestMapping("/open-api-games/v1/games-processor")
class GamesProcessorController(
    private val service: GamesProcessorService,
    private val validator: ValidationService
) {

    @PostMapping
    fun handleRequest(@RequestBody request: RequestDTO): ResponseDTO {
        validator.validateRequest(request)
        return service.handleRequest(request)
    }
}
