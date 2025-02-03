package com.cloudfilmtech.cloudfilm.repository;

import com.cloudfilmtech.cloudfilm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователя по email.
     *
     * @param email email пользователя
     * @return Optional с найденным пользователем или пустой, если пользователь не
     *         найден
     */
    Optional<User> findByEmail(String email);
}
