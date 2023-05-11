package org.unibl.etf.webshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.unibl.etf.webshop.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findCommentsByProductId(Long productId);
}
