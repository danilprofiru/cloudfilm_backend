package com.cloudfilmtech.cloudfilm.repository;

import com.cloudfilmtech.cloudfilm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Role.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Поиск роли по её имени.
     *
     * @param name имя роли
     * @return Optional с найденной ролью или пустой, если роль не найдена
     */
    Optional<Role> findByName(String name);
}
