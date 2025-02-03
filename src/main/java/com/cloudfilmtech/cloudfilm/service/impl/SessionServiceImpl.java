package com.cloudfilmtech.cloudfilm.service.impl;

import com.cloudfilmtech.cloudfilm.model.User;
import com.cloudfilmtech.cloudfilm.model.UserSession;
import com.cloudfilmtech.cloudfilm.repository.UserSessionRepository;
import com.cloudfilmtech.cloudfilm.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserSessionRepository sessionRepository;

    @Override
    public UserSession createSession(User user, String deviceInfo, String ipAddress) {
        UserSession session = new UserSession();
        session.setUser(user);
        // Генерируем уникальный токен сессии
        session.setSessionToken(UUID.randomUUID().toString());
        session.setUserAgent(deviceInfo);
        session.setIpAddress(ipAddress);
        session.setCreatedAt(LocalDateTime.now());
        // Задаем срок действия сессии (например, 30 дней)
        session.setExpiresAt(LocalDateTime.now().plusDays(30));
        return sessionRepository.save(session);
    }

    @Override
    public List<UserSession> getActiveSessions(User user) {
        // Возвращаем сессии, срок действия которых еще не истек
        return sessionRepository.findByUserAndExpiresAtAfter(user, LocalDateTime.now());
    }

    @Override
    public void invalidateSession(Long sessionId, User user) {
        UserSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Сессия не найдена"));
        if (!session.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Нет доступа к данной сессии");
        }
        // Инвалидация сессии — можно установить срок действия равным текущему времени
        session.setExpiresAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
