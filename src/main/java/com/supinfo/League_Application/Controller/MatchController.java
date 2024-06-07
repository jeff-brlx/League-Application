package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.IMatchRepository;
import com.supinfo.League_Application.Repository.ITeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/matches")
@Tag(name = "Match Management", description = "Operations pertaining to matches in the League Application")
public class MatchController {

    private final IMatchRepository matchRepository;
    private final ITeamRepository teamRepository;

    public MatchController(IMatchRepository matchRepository, ITeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Create a new match
     *
     * This method handles HTTP POST requests to create a new match.
     * It validates the input data and saves the match if the home and away teams are provided.
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
     * @param match The Match object to be created
     * @return The created Match object or a 400 status code if the input data is invalid
     */
    @Operation(summary = "Create a new match", description = "Create a new match by providing the match details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "400", description = "Invalid match data provided")
    })
    @PostMapping
    public ResponseEntity<Match> createMatch(
            @Parameter(description = "Match object to be stored in the database", required = true) @RequestBody Match match) {
        if (match.getTeamHome() == null || match.getTeamAway() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Match savedMatch = matchRepository.save(match);
        return ResponseEntity.ok(savedMatch);
    }

    /**
     * Get all matches
     *
     * This method handles HTTP GET requests to retrieve all matches.
     * It returns a list of matches or a 204 status code if no matches are found.
     *
     * @return A list of matches or a 204 status code if no matches are found
     */
    @Operation(summary = "Get all matches", description = "Retrieve a list of all matches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of matches", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "204", description = "No matches found")
    })
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        if (matches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(matches);
    }

    /**
     * Get a match by ID
     *
     * This method handles HTTP GET requests to retrieve a match by its ID.
     * It returns the match if found, or a 404 status code if not found.
     *
     * @param id The ID of the match to be retrieved
     * @return The Match object or a 404 status code if not found
     */
    @Operation(summary = "Get a match by ID", description = "Retrieve a match by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(
            @Parameter(description = "ID of the match to be retrieved", required = true) @PathVariable Long id) {
        return matchRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a match
     *
     * This method handles HTTP DELETE requests to delete a match by its ID.
     * It returns a 204 status code if the match is successfully deleted, or a 404 status code if not found.
     *
     * @param id The ID of the match to be deleted
     * @return A 204 status code if successfully deleted, or a 404 status code if not found
     */
    @Operation(summary = "Delete a match", description = "Delete a match by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted match"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(
            @Parameter(description = "ID of the match to be deleted", required = true) @PathVariable Long id) {
        if (!matchRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        matchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
