package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
}
