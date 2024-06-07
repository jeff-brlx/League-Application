package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByDayId(Long id);
}
