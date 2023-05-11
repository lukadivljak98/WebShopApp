package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.webshop.model.Comment;
import org.unibl.etf.webshop.request.CommentRequest;
import org.unibl.etf.webshop.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<Comment>> getAllCommentsByProductId(@PathVariable("id") Long productId){
        List<Comment> comments = commentService.findAllCommentsByProductId(productId);
        log.info("Success getting comments by productId on url: localhost:8080/comments/" + productId + ". List: " + comments);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request){
        Comment newComment = commentService.addComment(request);
        log.info("Success adding comment on url: localhost:8080/comments. Comment: " + newComment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }
}
