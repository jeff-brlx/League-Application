package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Entity.Team;
import com.supinfo.League_Application.Repository.ISeasonRepository;
import com.supinfo.League_Application.Repository.ITeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final ITeamRepository teamRepository;
    private final ISeasonRepository seasonRepository;


    public TeamController(ITeamRepository teamRepository , ISeasonRepository seasonRepository) {
        this.teamRepository = teamRepository;
        this.seasonRepository = seasonRepository;
    }

    // Créer une nouvelle équipe
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        if (team.getName() == null || team.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (teamRepository.existsByName(team.getName())) {
            return ResponseEntity.badRequest().build();
        }

        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    // Récupérer toutes les équipes
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teams);
    }

    // Récupérer une équipe par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Mettre à jour une équipe
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
        return teamRepository.findById(id).map(team -> {
            if (updatedTeam.getName() != null && !updatedTeam.getName().isEmpty()) {
                team.setName(updatedTeam.getName());
            }
            Team savedTeam = teamRepository.save(team);
            return ResponseEntity.ok(savedTeam);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer une équipe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        if (!teamRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        teamRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Ajouter une équipe à une saison
    @PostMapping("/{teamId}/season/{seasonId}")
    public ResponseEntity<Team> addTeamToSeason(@PathVariable Long teamId, @PathVariable Long seasonId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Season> seasonOptional = seasonRepository.findById(seasonId);

        if (teamOptional.isEmpty() || seasonOptional.isEmpty()) {
            return ResponseEntity.notFound().build();  // Retourne 404 si l'équipe ou la saison n'existe pas
        }

        Team team = teamOptional.get();
        Season season = seasonOptional.get();

        if (!team.getSeasons().contains(season)) {
            team.getSeasons().add(season);
            teamRepository.save(team);
        }

        return ResponseEntity.ok(team);
    }
}


