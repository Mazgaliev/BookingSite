package com.example.bookingsite.Model.Exception;

public class UserIsNotOwner extends RuntimeException {
    public UserIsNotOwner() {super("User is not and owner");}
}
