package com.cloudfilmtech.cloudfilm.service;

import com.cloudfilmtech.cloudfilm.model.User;

public interface AuthService {
    /**
     * Регистрация по email: создаёт пользователя с пустым паролем, генерирует и
     * отправляет токен подтверждения.
     *
     * @param email email для регистрации
     */
    void registerEmail(String email);

    /**
     * Подтверждение email по токену.
     *
     * @param token токен подтверждения
     * @return true, если подтверждение успешно, иначе false
     */
    boolean confirmEmail(String token);

    /**
     * Установка пароля для пользователя после подтверждения email.
     *
     * @param userId   идентификатор пользователя
     * @param password новый пароль
     */
    void setPassword(Long userId, String password);

    /**
     * Аутентификация пользователя (логин).
     *
     * @param email      email пользователя
     * @param password   пароль
     * @param deviceInfo информация об устройстве (User-Agent)
     * @param ipAddress  IP-адрес
     * @return объект пользователя при успешной аутентификации
     */
    User login(String email, String password, String deviceInfo, String ipAddress);

    /**
     * Смена email у пользователя.
     *
     * @param user     текущий пользователь
     * @param newEmail новый email
     */
    void changeEmail(User user, String newEmail);

    /**
     * Смена пароля у пользователя.
     *
     * @param user            текущий пользователь
     * @param currentPassword текущий пароль
     * @param newPassword     новый пароль
     */
    void changePassword(User user, String currentPassword, String newPassword);
}
