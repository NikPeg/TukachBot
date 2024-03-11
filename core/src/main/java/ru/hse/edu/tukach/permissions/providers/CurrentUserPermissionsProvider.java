package ru.hse.edu.tukach.permissions.providers;

import java.util.Set;

public interface CurrentUserPermissionsProvider {

    Set<String> currentUserPermissions();
}
