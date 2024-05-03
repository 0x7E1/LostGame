package tech.lostgame.test.dto.request

import tech.lostgame.test.dto.ContentDTO

data class RequestDTO(
    val api: String,
    val data: ContentDTO
)