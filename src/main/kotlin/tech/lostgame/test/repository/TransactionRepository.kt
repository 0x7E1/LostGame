package tech.lostgame.test.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech.lostgame.test.entity.Transaction

@Repository
interface TransactionRepository : JpaRepository<Transaction, String>