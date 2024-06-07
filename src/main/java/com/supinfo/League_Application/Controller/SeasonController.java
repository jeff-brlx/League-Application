package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Season;
import com.supinfo.League_Application.Repository.IDayRepository;
import com.supinfo.League_Application.Repository.ISeasonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

@RestController
@RequestMapping("/season")
@Tag(name = "Season Management", description = "Operations pertaining to seasons in the League Application")
public class SeasonController {

    private final ISeasonRepository seasonRepository;
    private final IDayRepository dayRepository;

    public SeasonController(ISeasonRepository seasonRepository, IDayRepository dayRepository) {
        this.seasonRepository = seasonRepository;
        this.dayRepository = dayRepository;
    }

    /**
     * Create a new season
     *
     * This method handles HTTP POST requests to create a new season.
     * It validates the input data and saves the season if the label is provided and unique.
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
     * @param season The Season object to be created
     * @return The created Season object or a 400 status code if the input data is invalid
     */
    @Operation(summary = "Create a new season", description = "Create a new season by providing the season details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created season", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Season.class))),
            @ApiResponse(responseCode = "400", description = "Invalid season data provided")
    })
    @PostMapping
    public ResponseEntity<Season> createSeason(
            @Parameter(description = "Season object to be stored in the database", required = true) @RequestBody Season season) {
        if (season.getLabel() == null) {
            return ResponseEntity.status(400).build();
        }
        if (seasonRepository.existsByLabel(season.getLabel())) {
            return ResponseEntity.status(400).build();
        }
        Season savedSeason = seasonRepository.save(season);
        return ResponseEntity.status(201).body(savedSeason);
    }

    /**
     * Delete a season
     *
     * This method handles HTTP DELETE requests to delete a season by its ID.
     * It checks if the season exists and if it has any associated days before deleting.
     *
     * @param id The ID of the season to be deleted
     * @return A 204 status code if successfully deleted, or a 404 status code if not found
     */
    @Operation(summary = "Delete a season", description = "Delete a season by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted season"),
            @ApiResponse(responseCode = "404", description = "Season not found"),
            @ApiResponse(responseCode = "400", description = "Season has associated days and cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeason(
            @Parameter(description = "ID of the season to be deleted", required = true) @PathVariable Long id) {
        if (!seasonRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (dayRepository.existsBySeasonId(id)) {
            return ResponseEntity.badRequest().body(null);
        }
        seasonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
