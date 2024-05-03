package tech.lostgame.test.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ContentDTO(
    var userId: String? = null,
    var userNick: String? = null,
    var gameSessionId: String? = null,
    var transactionId: String? = null,
    var currency: String? = null,
    var amount: Long? = null,
    var denomination: Int? = null,
    var maxWin: Long? = null,
    var jpKey: String? = null,
    var betId: String? = null,
    @JsonProperty("data")
    var metadata: MetadataDTO? = null,
    var api: String? = null
)

data class MetadataDTO(
    var betId: String? = null
)
