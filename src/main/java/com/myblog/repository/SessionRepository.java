package com.myblog.repository;

import com.myblog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);
}
