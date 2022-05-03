package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Review;

import java.util.List;

public interface ReviewService {


    Review createReview(Long personId, Long placeId, String description, Short rating);

    List<Review> findCommentsByPlaceId(Long id);

    Review findByPlaceIdAndPersonId(Long placeId, Long personId);


}
