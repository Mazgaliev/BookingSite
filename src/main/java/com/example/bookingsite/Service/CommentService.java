package com.example.bookingsite.Service;

import com.example.bookingsite.Model.Comment;
import com.example.bookingsite.Model.Place;

import java.util.List;
import java.util.Optional;

public interface CommentService {


    Comment createComment(Long personId, Long placeId, String description, Short rating);

    List<Comment> findCommentsByPlaceId(Long id);
}
