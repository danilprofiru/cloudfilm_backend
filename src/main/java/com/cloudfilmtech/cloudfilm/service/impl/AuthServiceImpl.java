package com.cloudfilmtech.cloudfilm.service.impl;

import com.cloudfilmtech.cloudfilm.model.Role;
import com.cloudfilmtech.cloudfilm.model.User;
import com.cloudfilmtech.cloudfilm.model.VerificationToken;
import com.cloudfilmtech.cloudfilm.repository.RoleRepository;
import com.cloudfilmtech.cloudfilm.repository.UserRepository;
import com.cloudfilmtech.cloudfilm.repository.VerificationTokenRepository;
import com.cloudfilmtech.cloudfilm.service.AuthService;
import com.cloudfilmtech.cloudfilm.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void registerEmail(String email) {
        // Проверка, существует ли пользователь с данным email
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email уже используется");
        }

        // Создаем нового пользователя с пустым паролем (пароль будет установлен после
        // подтверждения)
        User user = new User();
        user.setEmail(email);
        user.setPassword("");
        userRepository.save(user);

        // Генерируем токен подтверждения
        String tokenStr = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken();
        token.setToken(tokenStr);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        tokenRepository.save(token);

        // Отправляем письмо с подтверждением
        String confirmationLink = "http://localhost:8080/api/auth/confirm-email?token=" + tokenStr;
        String subject = "Подтверждение email";
        String content = "Перейдите по ссылке для подтверждения: " + confirmationLink;
        emailService.sendEmail(email, subject, content);
    }

    @Override
    public boolean confirmEmail(String tokenStr) {
        Optional<VerificationToken> tokenOpt = tokenRepository.findByToken(tokenStr);
        if (!tokenOpt.isPresent()) {
            return false;
        }
        VerificationToken token = tokenOpt.get();
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        // Удаляем токен после успешного подтверждения
        tokenRepository.delete(token);
        return true;
    }

    @Override
    public void setPassword(Long userId, String password) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("Пользователь не найден");
        }
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(password));

        // Назначаем роль "ROLE_USER" по умолчанию
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.getRoles().add(userRole);

        userRepository.save(user);
    }

    @Override
    public User login(String email, String password, String deviceInfo, String ipAddress) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Override
    public void changeEmail(User user, String newEmail) {
        // В реальном приложении следует отправить письмо с подтверждением нового email
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Текущий пароль неверен");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
