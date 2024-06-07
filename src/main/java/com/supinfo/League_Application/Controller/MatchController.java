package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.IMatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final IMatchRepository matchRepository;

    public MatchController(IMatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // Créer un match
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        if (match.getMatchDate() == null || match.getMatchDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(null);
        }
        if (match.getTeamHome() == null || match.getTeamAway() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Match savedMatch = matchRepository.save(match);
        return ResponseEntity.ok(savedMatch);
    }

    // Récupérer tous les matchs d'une journée
    @GetMapping("/day/{dayId}")
    public ResponseEntity<List<Match>> getMatchesByDay(@PathVariable Long dayId) {
        List<Match> matches = matchRepository.findByDayId(dayId);
        if (matches.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }

    // Modifier les horaires d'un match
  /*  @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatchTime(@PathVariable Long id, @RequestBody LocalDateTime newMatchDate) {
        return (ResponseEntity<Match>) matchRepository.findById(id).map(match -> {
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