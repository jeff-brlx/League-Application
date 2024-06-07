package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDayRepository extends JpaRepository<Day, Long> {
    boolean existsBySeasonId(Long seasonId);
}
