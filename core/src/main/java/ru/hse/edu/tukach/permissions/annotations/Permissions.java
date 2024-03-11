package ru.hse.edu.tukach.permissions.annotations;

import ru.hse.edu.tukach.permissions.PermissionMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для перечисления разрешений необходимых пользователю для доступа к методу.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {

    String[] permissions() default {};

    /**
     * Режим работы - допуск по любому из перечисленных разрешений (по умолчанию), или по всем.
     */
    PermissionMode mode() default PermissionMode.ALL;
}
