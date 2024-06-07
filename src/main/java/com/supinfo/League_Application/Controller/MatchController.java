package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Entity.Team;
import com.supinfo.League_Application.Repository.IMatchRepository;
import com.supinfo.League_Application.Repository.ITeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final IMatchRepository matchRepository;
    private final ITeamRepository teamRepository;


    public MatchController(IMatchRepository matchRepository, ITeamRepository teamRepository ) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;

    }

    // Créer un match
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        if (match.getTeamHome() == null || match.getTeamAway() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Match savedMatch = matchRepository.save(match);
        return ResponseEntity.ok(savedMatch);
    }
    // Récupérer tous les matchs
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        if (matches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(matches);
    }

    // Récupérer un match par ID
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {

        return matchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Modifier les horaires d'un match
    /*@PutMapping("/{id}")
    public ResponseEntity<Match> updateMatchTime(@PathVariable Long id, @RequestBody LocalDateTime newMatchDate) {
        return matchRepository.findById(id).map(match -> {
            if (newMatchDate != null && !newMatchDate.isBefore(LocalDateTime.now())) {
                match.setMatchDate(newMatchDate);
                return ResponseEntity.ok(matchRepository.save(match));
            }
            return ResponseEntity.badRequest().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    // Supprimer un match
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        if (!matchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        matchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
