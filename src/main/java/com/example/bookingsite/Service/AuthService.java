package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Person;

public interface AuthService {
    Person login(String username, String password);
}
