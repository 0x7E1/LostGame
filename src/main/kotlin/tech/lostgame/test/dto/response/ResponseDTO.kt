package tech.lostgame.test.dto.response

import tech.lostgame.test.dto.ContentDTO

data class ResponseDTO(
    var api: String,
    var isSuccess: Boolean? = null,
    var error: String? = null,
    var errorMsg: String? = null,
    var data: ContentDTO? = null
)
