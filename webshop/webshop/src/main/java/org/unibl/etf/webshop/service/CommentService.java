package org.unibl.etf.webshop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.AppUserNotFoundException;
import org.unibl.etf.webshop.exception.ProductNotFoundException;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.Comment;
import org.unibl.etf.webshop.model.Product;
import org.unibl.etf.webshop.repo.AppUserRepo;
import org.unibl.etf.webshop.repo.CommentRepo;
import org.unibl.etf.webshop.repo.ProductRepo;
import org.unibl.etf.webshop.request.CommentRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentService {
    private final CommentRepo commentRepo;
    private final AppUserRepo appUserRepo;
    private final ProductRepo productRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, AppUserRepo appUserRepo, ProductRepo productRepo){
        this.commentRepo = commentRepo;
        this.appUserRepo = appUserRepo;
        this.productRepo = productRepo;
    }

    public List<Comment> findAllCommentsByProductId(Long productId) {
        return commentRepo.findCommentsByProductId(productId).orElseThrow(() ->{
            log.error("Product by id: " + productId + " was not found");
                return new ProductNotFoundException("Product by id: " + productId + " was not found");});
    }

    public Comment addComment(CommentRequest request) {
        Comment comment = new Comment();
        comment.setMessage(request.getMessage());
        AppUser sender = appUserRepo.findAppUserByUsername(request.getSender()).orElseThrow(()->{
            log.error("User with username: " + request.getSender() + " not found");
                return new AppUserNotFoundException("User with username: " + request.getSender() + " not found");});
        Product product = productRepo.findProductById(request.getProductId()).orElseThrow(()->{
            log.error("Product with id: " + request.getProductId() + " not found");
                return new ProductNotFoundException("Product with id: " + request.getProductId() + " not found");}
        );
        comment.setSender(sender);
        comment.setProduct(product);
        comment.setTime(LocalDateTime.now());
        return commentRepo.save(comment);
    }
}
