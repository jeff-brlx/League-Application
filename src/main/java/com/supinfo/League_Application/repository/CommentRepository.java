package com.supinfo.League_Application.repository;



import com.supinfo.League_Application.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMatchId(Long matchId);
}
