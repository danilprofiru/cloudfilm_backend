package com.cloudfilmtech.cloudfilm.service;

import com.cloudfilmtech.cloudfilm.model.User;
import com.cloudfilmtech.cloudfilm.model.UserSession;

import java.util.List;

public interface SessionService {
    /**
     * Создает новую сессию для пользователя.
     *
     * @param user       пользователь, для которого создается сессия
     * @param deviceInfo информация об устройстве (User-Agent)
     * @param ipAddress  IP-адрес
     * @return созданная сессия
     */
    UserSession createSession(User user, String deviceInfo, String ipAddress);

    /**
     * Возвращает список активных сессий пользователя.
     *
     * @param user пользователь
     * @return список активных сессий
     */
    List<UserSession> getActiveSessions(User user);

    /**
     * Инвалидация (завершение) сессии.
     *
     * @param sessionId идентификатор сессии
     * @param user      пользователь, которому принадлежит сессия
     */
    void invalidateSession(Long sessionId, User user);
}
