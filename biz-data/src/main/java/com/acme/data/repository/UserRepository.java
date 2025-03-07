package com.acme.data.repository;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * Repository 实现透明化(SQL NoSQL)
 * Spring Data Commons 能支持自定义存储引擎
 * @author: wuhao
 * @time: 2025/3/4 17:27
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface UserRepository extends CrudRepository {
    //CRUD
}
