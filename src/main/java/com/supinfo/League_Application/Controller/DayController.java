package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Day;
import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Repository.IDayRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/day")
public class DayController {
    private final IDayRepository dayRepository;
    public DayController(IDayRepository dayRepository){
        this.dayRepository= dayRepository;
    }
    @PostMapping
    public ResponseEntity<Day> createDay(@RequestBody Day day){
        if (day.getDayNumber() == null || day.getDate() == null || day.getSeason()== null) {
            return ResponseEntity.status(400).build();
        }
        if (dayRepository.existsByDayNumberAndSeasonId(day.getDayNumber(), day.getSeason().getId())) {
            return ResponseEntity.status(400).build();
        }
        Day savedDay = dayRepository.save(day);
        return ResponseEntity.status(201).body(savedDay);
    }
}
