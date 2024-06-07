package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Entity.Team;
import com.supinfo.League_Application.Repository.ISeasonRepository;
import com.supinfo.League_Application.Repository.ITeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@Tag(name = "Team Management", description = "Operations pertaining to teams in the League Application")
public class TeamController {

    private final ITeamRepository teamRepository;
    private final ISeasonRepository seasonRepository;

    public TeamController(ITeamRepository teamRepository, ISeasonRepository seasonRepository) {
        this.teamRepository = teamRepository;
        this.seasonRepository = seasonRepository;
    }

    /**
     * Create a new team
     *
     * This method handles HTTP POST requests to create a new team.
     * It validates the input data and saves the team if the name is provided and unique.
     *
     * Swagger documentation is provided using the springdoc-openapi library.
     * The annotations below describe the API operation, parameters, and responses.
     *
     * Dependency used for Swagger documentation:
     * <dependency>
     *     <groupId>org.springdoc</groupId>
     *     <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
     *     <version>2.5.0</version>
     * </dependency>
     *
     * @param team The Team object to be created
     * @return The created Team object or a 400 status code if the input data is invalid
     */
    @Operation(summary = "Create a new team", description = "Create a new team by providing the team details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))),
            @ApiResponse(responseCode = "400", description = "Invalid team data provided")
    })
    @PostMapping
    public ResponseEntity<Team> createTeam(
            @Parameter(description = "Team object to be stored in the database", required = true) @RequestBody Team team) {
        if (team.getName() == null || team.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (teamRepository.existsByName(team.getName())) {
            return ResponseEntity.badRequest().build();
        }

        Team savedTeam = teamRepository.save(team);
        return ResponseEntity.ok(savedTeam);
    }

    /**
     * Get all teams
     *
     * This method handles HTTP GET requests to retrieve all teams.
     * It returns a list of teams or a 204 status code if no teams are found.
     *
     * @return A list of teams or a 204 status code if no teams are found
     */
    @Operation(summary = "Get all teams", description = "Retrieve a list of all teams")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of teams", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))),
            @ApiResponse(responseCode = "204", description = "No teams found")
    })
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teams);
    }

    /**
     * Get a team by ID
     *
     * This method handles HTTP GET requests to retrieve a team by its ID.
     * It returns the team if found, or a 404 status code if not found.
     *
     * @param id The ID of the team to be retrieved
     * @return The Team object or a 404 status code if not found
     */
    @Operation(summary = "Get a team by ID", description = "Retrieve a team by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(
            @Parameter(description = "ID of the team to be retrieved", required = true) @PathVariable Long id) {
        return teamRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update a team
     *
     * This method handles HTTP PUT requests to update a team's details.
     * It validates the input data and updates the team's name if provided.
     *
     * @param id The ID of the team to be updated
     * @param updatedTeam The updated Team object
     * @return The updated Team object or a 404 status code if the team is not found
     */
    @Operation(summary = "Update a team", description = "Update a team's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated team", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(
            @Parameter(description = "ID of the team to be updated", required = true) @PathVariable Long id,
            @Parameter(description = "Updated team object", required = true) @RequestBody Team updatedTeam) {
        return teamRepository.findById(id).map(team -> {
            if (updatedTeam.getName() != null && !updatedTeam.getName().isEmpty()) {
                team.setName(updatedTeam.getName());
            }
            Team savedTeam = teamRepository.save(team);
            return ResponseEntity.ok(savedTeam);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a team
     *
     * This method handles HTTP DELETE requests to delete a team by its ID.
     * It returns a 204 status code if the team is successfully deleted, or a 404 status code if not found.
     *
     * @param id The ID of the team to be deleted
     * @return A 204 status code if successfully deleted, or a 404 status code if not found
     */
    @Operation(summary = "Delete a team", description = "Delete a team by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted team"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(
            @Parameter(description = "ID of the team to be deleted", required = true) @PathVariable Long id) {
        if (!teamRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        teamRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Add a team to a season
     *
     * This method handles HTTP POST requests to add a team to a season.
     * It checks if the team and season exist before adding the team to the season.
     *
     * @param teamId The ID of the team to be added to the season
     * @param seasonId The ID of the season to add the team to
     * @return The updated Team object or a 404 status code if the team or season is not found
     */
    @Operation(summary = "Add a team to a season", description = "Add a team to a season by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added team to season", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Team.class))),
            @ApiResponse(responseCode = "404", description = "Team or season not found")
    })
    @PostMapping("/{teamId}/season/{seasonId}")
    public ResponseEntity<Team> addTeamToSeason(
            @Parameter(description = "ID of the team to be added to the season", required = true) @PathVariable Long teamId,
            @Parameter(description = "ID of the season to add the team to", required = true) @PathVariable Long seasonId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        Optional<Season> seasonOptional = seasonRepository.findById(seasonId);

        if (teamOptional.isEmpty() || seasonOptional.isEmpty()) {
            return ResponseEntity.notFound().build();  // Return 404 if the team or season does not exist
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
