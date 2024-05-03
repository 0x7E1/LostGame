package tech.lostgame.test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.lostgame.test.entity.GameSession

@Repository
interface SessionRepository : JpaRepository<GameSession, String>