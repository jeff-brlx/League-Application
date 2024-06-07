package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Day;
import com.supinfo.League_Application.Repository.IDayRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

@RestController
@RequestMapping("/day")
@Tag(name = "Day Management", description = "Operations pertaining to Day in Day Management System")
public class DayController {

    private final IDayRepository dayRepository;

    public DayController(IDayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    /**
     * Create a new Day
     *
     * This method handles HTTP POST requests to create a new Day.
     * It validates the input data and checks for existing entries
     * with the same day number and season before saving the new Day.
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
     * @param day The Day object to be created
     * @return The created Day object or a 400 status code if input data is invalid or a conflict occurs
     */
    @Operation(summary = "Add a new day", description = "Create a new day in the Day Management System", responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created day", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Day.class))),
            @ApiResponse(responseCode = "400", description = "Bad request due to missing or invalid data")
    })
    @PostMapping
    public ResponseEntity<Day> createDay(
            @Parameter(description = "Day object to be stored in the database", required = true) @RequestBody Day day) {
        if (day.getDayNumber() == null || day.getDate() == null || day.getSeason() == null) {
            return ResponseEntity.status(400).build();
        }
        if (dayRepository.existsByDayNumberAndSeasonId(day.getDayNumber(), day.getSeason().getId())) {
            return ResponseEntity.status(400).build();
        }
        Day savedDay = dayRepository.save(day);
        return ResponseEntity.status(201).body(savedDay);
    }
}
