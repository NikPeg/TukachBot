package ru.hse.edu.tukach.permissions.aspect;

import org.aspectj.lang.annotation.Aspect;
import ru.hse.edu.tukach.permissions.PermissionsChecker;

@Aspect
public class DefaultPermissionAspect extends AbstractPermissionsAspect {

    public DefaultPermissionAspect(PermissionsChecker permissionsChecker) {
        super(permissionsChecker);
    }
}
