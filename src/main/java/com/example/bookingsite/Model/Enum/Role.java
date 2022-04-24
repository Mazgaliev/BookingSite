package com.example.bookingsite.Model.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_STANDARD,
    ROLE_OWNER;

    @Override
    public String getAuthority() {
        return name();
    }
}
