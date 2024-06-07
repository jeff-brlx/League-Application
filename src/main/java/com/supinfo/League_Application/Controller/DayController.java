package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Day;
import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Repository.IDayRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class DayController {
    private final IDayRepository dayRepository;
    public DayController(IDayRepository dayRepository){
        this.dayRepository= dayRepository;
    }
    @PostMapping
    public ResponseEntity<Season> create(@RequestBody Day day){
        if (day.getDayNumber() == null || day.getDate() == null || day.getSeason()== null) {
            return ResponseEntity.status(400).build();
        }
        if (seasonRepository.existsByLabel(season.getLabel())) {
            return ResponseEntity.status(400).build();
        }
        Season savedSeason = seasonRepository.save(season);
        return ResponseEntity.status(201).body(savedSeason);
    }
}
