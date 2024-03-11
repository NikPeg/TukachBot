package ru.hse.edu.tukach.permissions.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hse.edu.tukach.model.permission.Permission;
import ru.hse.edu.tukach.model.role.Role;
import ru.hse.edu.tukach.permissions.exceptions.RoleDataException;
import ru.hse.edu.tukach.repository.role.RoleRepository;

import java.util.Set;

/**
 * Сервис рыботы с ролями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Создание роли
     */
    public Role create(Role dto) {
        validateRoleName(dto.getName());

        roleRepository.save(new Role());
        return new Role();
    }

    public Set<Permission> getAllPermissionsForRole(Role role) {
        return null;
    }

    private void validateRoleName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new RoleDataException("Имя роли не задано");
        }
        if (roleRepository.existsByName(name)) {
            throw new RoleDataException("Роль с таким именем уже существует");
        }
    }
}
