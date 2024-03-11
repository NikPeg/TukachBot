package ru.hse.edu.tukach.permissions.exceptions;

import lombok.Getter;
import lombok.Setter;
import ru.hse.edu.tukach.permissions.PermissionMode;

import java.util.Set;

@Getter
@Setter
public class NoPermissionException extends RuntimeException {

    private Set<String> requiredPermissions;
    private Set<String> userPermissions;
    private PermissionMode permissionMode;
    private String methodName;
    private String className;

    public NoPermissionException() {
        super();
    }

    public NoPermissionException(String message) {
        super(message);
    }

    /**
     * @param message             сообщение
     * @param requiredPermissions разрешения, которые были необходимы для доступа к методу
     * @param userPermissions     разрешения, которые были у поьзователя в момент попытки вызова метода
     * @param permissionMode      режим проверки разрешений
     * @param methodName          имя метода который пытались вызвать
     * @param className           имя класса, в котором находится вызванный метод
     */
    public NoPermissionException(String message, Set<String> requiredPermissions, Set<String> userPermissions,
                                 PermissionMode permissionMode, String methodName, String className) {
        super(message);
        this.requiredPermissions = requiredPermissions;
        this.userPermissions = userPermissions;
        this.permissionMode = permissionMode;
        this.methodName = methodName;
        this.className = className;
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
    }

    protected NoPermissionException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
