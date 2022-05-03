package com.example.bookingsite.Model.Exception;

public class PersonHasNotReviewedPlaceYetException extends RuntimeException{

    public PersonHasNotReviewedPlaceYetException() {
        super("Not reviewed yet");
    }
}
