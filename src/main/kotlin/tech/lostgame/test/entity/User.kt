package tech.lostgame.test.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var userId: String,
    var username: String,
    var balance: Long,
    var currencies: List<String>,
    var denomination: Int,
    var maxWin: Long,
    var jpKey: String?
)
