package tech.lostgame.test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tech.lostgame.test.entity.User

@Repository
interface UserRepository : JpaRepository<User, String> {

    @Query(
        value = "SELECT u.* FROM game-session as gs " +
            "INNER JOIN user as u ON gs.user_id = u.user_id " +
            "WHERE gs.game_session_id = :sessionId",
        nativeQuery = true
    )
    fun findBySessionId(sessionId: String): User
}