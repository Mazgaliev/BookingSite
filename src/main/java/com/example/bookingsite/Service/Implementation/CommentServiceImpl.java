package com.example.bookingsite.Service.Implementation;

import com.example.bookingsite.Model.Comment;
import com.example.bookingsite.Model.Exception.PersonDoesNotExistException;
import com.example.bookingsite.Model.Exception.PlaceDoesNotExistException;
import com.example.bookingsite.Model.Person;
import com.example.bookingsite.Model.Place;
import com.example.bookingsite.Repository.CommentRepository;
import com.example.bookingsite.Repository.PersonRepository;
import com.example.bookingsite.Repository.PlaceRepository;
import com.example.bookingsite.Service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PersonRepository personRepository;

    private final PlaceRepository placeRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PersonRepository personRepository, PlaceRepository placeRepository) {
        this.commentRepository = commentRepository;
        this.personRepository = personRepository;
        this.placeRepository = placeRepository;
    }


    @Override
    public Comment createComment(Long personId, Long placeId, String description, Short rating) {
        Person person = this.personRepository.findById(personId).orElseThrow(PersonDoesNotExistException::new);
        Place place = this.placeRepository.findById(placeId).orElseThrow(PlaceDoesNotExistException::new);
        Comment comment = new Comment(description, person, place, rating);
        this.commentRepository.save(comment);
        return comment;
    }

    @Override
    public List<Comment> findCommentsByPlaceId(Long id) {
        return this.commentRepository.findAllByPlaceId(id);
    }
}
