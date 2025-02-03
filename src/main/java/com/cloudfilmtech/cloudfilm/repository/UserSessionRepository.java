package com.cloudfilmtech.cloudfilm.repository;

import com.cloudfilmtech.cloudfilm.model.User;
import com.cloudfilmtech.cloudfilm.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    List<UserSession> findByUserAndActiveTrue(User user);
}
