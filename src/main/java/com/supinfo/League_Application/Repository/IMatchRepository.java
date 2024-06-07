package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMatchRepository extends JpaRepository<Match, Long> {
}
