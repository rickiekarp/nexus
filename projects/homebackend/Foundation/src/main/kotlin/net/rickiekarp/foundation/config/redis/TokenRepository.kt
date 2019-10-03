package net.rickiekarp.foundation.config.redis

import net.rickiekarp.foundation.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : CrudRepository<User, String>