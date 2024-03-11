package ru.hse.edu.tukach.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hse.edu.tukach.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);
}
