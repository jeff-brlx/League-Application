package com.supinfo.League_Application.Repository;

import com.supinfo.League_Application.Entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeasonRepository  extends JpaRepository<Season,Long>  {

    boolean existsByLabel(String label);
}
