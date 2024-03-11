package ru.hse.edu.tukach.permissions.exceptions;

/**
 * Эксепшен для ситуаций, когда что-то пошло не так при обработке ролей
 */
public class RoleDataException extends RuntimeException {

    public RoleDataException(String message) {
        super(message);
    }
}
