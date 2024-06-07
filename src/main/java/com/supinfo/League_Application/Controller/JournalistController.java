package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Controller.exception.MatchNotFoundException;
import com.supinfo.League_Application.Controller.exception.UnauthorizedException;
import com.supinfo.League_Application.Entity.Comment;
import com.supinfo.League_Application.Entity.Event;
import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.CommentRepository;
import com.supinfo.League_Application.Repository.EventRepository;
import com.supinfo.League_Application.Repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;

import java.util.List;

@RestController
@RequestMapping("/journalist")
@Tag(name = "Journalist Management", description = "Operations for journalists to manage matches, comments, and events")
public class JournalistController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Start a match
     *
     * This method handles HTTP POST requests to start a match.
     * It checks the role of the user and updates the status of the match to "ongoing".
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
     * @param id The ID of the match to be started
     * @param role The role of the user
     * @return The updated match object or a 401/404 status code if unauthorized or match not found
     */
    @Operation(summary = "Start a match", description = "Start a match by changing its status to ongoing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully started match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @PostMapping("/start/{id}")
    public ResponseEntity<?> startMatch(
            @Parameter(description = "ID of the match to be started", required = true) @PathVariable Long id,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        match.setStatus("ongoing");
        Match updatedMatch = matchRepository.save(match);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    /**
     * End a match
     *
     * This method handles HTTP POST requests to end a match.
     * It checks the role of the user and updates the status of the match to "finished".
     *
     * @param id The ID of the match to be ended
     * @param role The role of the user
     * @return The updated match object or a 401/404 status code if unauthorized or match not found
     */
    @Operation(summary = "End a match", description = "End a match by changing its status to finished")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully ended match", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Match.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @PostMapping("/end/{id}")
    public ResponseEntity<?> endMatch(
            @Parameter(description = "ID of the match to be ended", required = true) @PathVariable Long id,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        match.setStatus("finished");
        Match updatedMatch = matchRepository.save(match);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    /**
     * Add a comment to a match
     *
     * This method handles HTTP POST requests to add a comment to a match.
     * It checks the role of the user and saves the comment.
     *
     * @param matchId The ID of the match to add a comment to
     * @param content The content of the comment
     * @param role The role of the user
     * @return The created comment object or a 401/404 status code if unauthorized or match not found
     */
    @Operation(summary = "Add a comment", description = "Add a comment to a match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added comment", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @PostMapping("/comment/{matchId}")
    public Comment addComment(
            @Parameter(description = "ID of the match to add a comment to", required = true) @PathVariable Long matchId,
            @Parameter(description = "Content of the comment", required = true) @RequestBody String content,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setMatch(match);
        return commentRepository.save(comment);
    }

    /**
     * Get comments for a match
     *
     * This method handles HTTP GET requests to retrieve comments for a match.
     * It checks the role of the user and returns the comments.
     *
     * @param matchId The ID of the match to retrieve comments for
     * @param role The role of the user
     * @return A list of comments or a 401/404 status code if unauthorized or match not found
     */
    @Operation(summary = "Get comments", description = "Retrieve comments for a match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @GetMapping("/comments/{matchId}")
    public List<Comment> getComments(
            @Parameter(description = "ID of the match to retrieve comments for", required = true) @PathVariable Long matchId,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist") && !role.equalsIgnoreCase("memberleague")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        return commentRepository.findByMatchId(matchId);
    }

    /**
     * Add an event to a match
     *
     * This method handles HTTP POST requests to add an event to a match.
     * It checks the role of the user and saves the event.
     *
     * @param matchId The ID of the match to add an event to
     * @param event The event object to be added
     * @param role The role of the user
     * @return The created event object or a 401/404 status code if unauthorized or match not found
     */
    @Operation(summary = "Add an event", description = "Add an event to a match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added event", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Match not found")
    })
    @PostMapping("/event/{matchId}")
    public Event addEvent(
            @Parameter(description = "ID of the match to add an event to", required = true) @PathVariable Long matchId,
            @Parameter(description = "Event object to be added", required = true) @RequestBody Event event,
            @Parameter(description = "Role of the user", required = true) @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        event.setMatch(match);
        return eventRepository.save(event);
    }

}
