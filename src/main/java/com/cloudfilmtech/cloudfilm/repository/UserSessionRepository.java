package com.cloudfilmtech.cloudfilm.repository;

import com.cloudfilmtech.cloudfilm.model.User;
import com.cloudfilmtech.cloudfilm.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью UserSession.
 */
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    /**
     * Поиск активных сессий пользователя (сессии, срок действия которых ещё не
     * истёк).
     *
     * @param user пользователь
     * @param now  текущее время
     * @return список активных сессий
     */
    List<UserSession> findByUserAndExpiresAtAfter(User user, LocalDateTime now);
}
