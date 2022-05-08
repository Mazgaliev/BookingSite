package com.example.bookingsite.Model.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_OWNER;

    public static Role getRoleFromString(String role){
        return switch (role) {
            case "ROLE_USER" -> Role.ROLE_USER;
            case "ROLE_OWNER" -> Role.ROLE_OWNER;
            default -> Role.ROLE_USER;
        };
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
