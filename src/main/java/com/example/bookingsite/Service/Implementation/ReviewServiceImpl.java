package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Exception.PlaceHasNoReservationsException;
import com.example.bookingsite.Model.Reservation;
import com.example.bookingsite.Model.Review;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.PlaceDoesNotExistException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Repository.ReviewRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.PlaceRepository;
import com.example.bookingsite.Service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PersonRepository personRepository;

    private final PlaceRepository placeRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PersonRepository personRepository, PlaceRepository placeRepository) {
        this.reviewRepository = reviewRepository;
        this.personRepository = personRepository;
        this.placeRepository = placeRepository;
    }


    @Override
    public Review createReview(Long personId, Long placeId, String description, Short rating) {
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        Place place = this.placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        Review review = new Review(description, person, place, rating);
        this.reviewRepository.save(review);
        List<Review> reviews = place.getReviews();
        Double newRating = newReviewValue(reviews);
        place.setRating(newRating);
        this.placeRepository.save(place);
        return review;
    }

    @Override
    public List<Review> findCommentsByPlaceId(Long id) {
        return this.reviewRepository.findAllByPlaceId(id);
    }

    @Override
    public Review findByPlaceIdAndPersonId(Long placeId, Long personId) {
        return this.reviewRepository.findByPerson_IdAndPlace_Id(personId, placeId).orElseThrow();
    }

    private double newReviewValue(List<Review> reviews) {

        return reviews.stream().mapToDouble(Review::getRating).average().orElseThrow(PlaceHasNoReservationsException::new);
    }
}
