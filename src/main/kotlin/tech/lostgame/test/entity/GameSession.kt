package tech.lostgame.test.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import tech.lostgame.test.entity.enums.SessionStatus

@Entity(name = "game-sessions")
data class GameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var gameSessionId: String?,
    var userId: String,
    var status: SessionStatus? = SessionStatus.ACTIVE
)
