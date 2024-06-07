package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.IMatchRepository;
import com.supinfo.League_Application.Controller.exception.UnauthorizedException;
import com.supinfo.League_Application.Controller.exception.InvalidMatchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

@RestController
@RequestMapping("/member-league")
@Tag(name = "Member League Management", description = "Operations for member league officials to manage match statuses")
public class MemberLeagueController {

    @Autowired
    private IMatchRepository matchRepository;

    /**
     * Report a match
     *
     * This method handles HTTP POST requests to report a match.
     * It checks the role of the user and updates the status of the match to "reported".
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
     * @param id The ID of the match to be reported
     * @param reason The reason for reporting the match
     * @param role The role of the user
     * @return The updated Match object or a status code if unauthorized or match not found
     */
    @Operation(summary = "Report a match", description = "Report a match and change its status to reported")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully reported match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found"),
            @ApiResponse(responseCode = "400", description = "Invalid match status")
    })
    @PostMapping("/report-match/{id}")
    public Match reportMatch(
            @Parameter(description = "ID of the match to be reported", required = true) @PathVariable Long id,
            @Parameter(description = "Reason for reporting the match", required = true) @RequestBody String reason,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("memberleague")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
        if (!"scheduled".equals(match.getStatus())) {
            throw new InvalidMatchStatusException("Only scheduled matches can be reported");
        }
        match.setStatus("reported");

        return matchRepository.save(match);
    }

    /**
     * Suspend a match
     *
     * This method handles HTTP POST requests to suspend a match.
     * It checks the role of the user and updates the status of the match to "suspended".
     *
     * @param id The ID of the match to be suspended
     * @param reason The reason for suspending the match
     * @param role The role of the user
     * @return The updated Match object or a status code if unauthorized or match not found
     */
    @Operation(summary = "Suspend a match", description = "Suspend a match and change its status to suspended")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully suspended match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found"),
            @ApiResponse(responseCode = "400", description = "Invalid match status")
    })
    @PostMapping("/suspend-match/{id}")
    public Match suspendMatch(
            @Parameter(description = "ID of the match to be suspended", required = true) @PathVariable Long id,
            @Parameter(description = "Reason for suspending the match", required = true) @RequestBody String reason,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("memberleague")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
        if (!"ongoing".equals(match.getStatus())) {
            throw new InvalidMatchStatusException("Only ongoing matches can be suspended");
        }
        match.setStatus("suspended");

        return matchRepository.save(match);
    }
}
