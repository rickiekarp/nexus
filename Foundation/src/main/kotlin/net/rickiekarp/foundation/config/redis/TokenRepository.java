package net.rickiekarp.foundation.config.redis;

import net.rickiekarp.foundation.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<User, String> {}