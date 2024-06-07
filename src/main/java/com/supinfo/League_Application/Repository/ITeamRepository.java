package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END FROM Team t JOIN t.seasons ts WHERE t.id = :teamId AND ts.id = :seasonId")
    boolean isTeamInSeason(@Param("teamId") Long teamId, @Param("seasonId") Long seasonId);
    boolean existsByName(String name);
}
