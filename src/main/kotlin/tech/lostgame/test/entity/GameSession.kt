package tech.lostgame.test.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import tech.lostgame.test.entity.enums.SessionStatus

@Entity(name = "game-sessions")
data class GameSession(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var gameSessionId: String?,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
    var status: SessionStatus? = SessionStatus.ACTIVE
)
