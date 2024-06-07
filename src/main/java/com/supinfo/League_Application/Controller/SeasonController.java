package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Repository.IDayRepository;
import com.supinfo.League_Application.Repository.ISeasonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/season")
public class SeasonController {
    private final ISeasonRepository seasonRepository;
    private final IDayRepository dayRepository;
    public SeasonController(ISeasonRepository seasonRepository,IDayRepository dayRepository){
        this.seasonRepository = seasonRepository;
        this.dayRepository = dayRepository;
    }

    @PostMapping
    public ResponseEntity<Season> createSeason(@RequestBody Season season){
        if (season.getLabel() == null ) {
            return ResponseEntity.status(400).build();
        }
        if (seasonRepository.existsByLabel(season.getLabel())) {
            return ResponseEntity.status(400).build();
        }
        Season savedSeason = seasonRepository.save(season);
        return ResponseEntity.status(201).body(savedSeason);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Long id){
        if (!seasonRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (dayRepository.existsBySeasonId(id)) {
            return ResponseEntity.badRequest().body(null);
        }
        seasonRepository.deleteById(id);
        return ResponseEntity.noContent().build();//revoir le message d'erreur
    }
}
