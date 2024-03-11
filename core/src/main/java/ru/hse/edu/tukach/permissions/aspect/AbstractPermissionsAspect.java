package ru.hse.edu.tukach.permissions.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import ru.hse.edu.tukach.permissions.PermissionsChecker;

/**
 * Аспект, проверяющий доступ пользователя к методам,
 * аннотированным {@link ru.hse.edu.tukach.permissions.annotations.Permissions}
 */
@Aspect
public abstract class AbstractPermissionsAspect {

    protected PermissionsChecker permissionsChecker;

    public AbstractPermissionsAspect(PermissionsChecker permissionsChecker) {
        this.permissionsChecker = permissionsChecker;
    }

    @Pointcut("@annotation(ru.hse.edu.tukach.permissions.annotations.Permissions)")
    public void permissions() {
    }

    @Around("permissions()")
    public Object checkPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        permissionsChecker.checkPermissions(signature.getMethod());
        return joinPoint.proceed();
    }
}
