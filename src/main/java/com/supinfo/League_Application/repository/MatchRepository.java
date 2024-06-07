package com.supinfo.League_Application.Repository;


import com.supinfo.League_Application.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}