package com.cloudfilmtech.cloudfilm.repository;

import com.cloudfilmtech.cloudfilm.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью VerificationToken.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    /**
     * Поиск токена подтверждения по значению токена.
     *
     * @param token строковое представление токена
     * @return Optional с найденным токеном или пустой, если токен не найден
     */
    Optional<VerificationToken> findByToken(String token);
}
