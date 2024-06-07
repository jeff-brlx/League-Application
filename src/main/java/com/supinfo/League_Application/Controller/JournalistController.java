package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Controller.exception.MatchNotFoundException;
import com.supinfo.League_Application.Entity.Comment;
import com.supinfo.League_Application.Entity.Event;
import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.CommentRepository;
import com.supinfo.League_Application.Repository.EventRepository;
import com.supinfo.League_Application.Repository.MatchRepository;
import com.supinfo.League_Application.Controller.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journalist")
public class JournalistController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private EventRepository eventRepository;

    public ResponseEntity<?> startMatch(@PathVariable Long id, @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        match.setStatus("ongoing");
        Match updatedMatch = matchRepository.save(match);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    public ResponseEntity<?> endMatch(@PathVariable Long id, @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        match.setStatus("finished");
        Match updatedMatch = matchRepository.save(match);
        return new ResponseEntity<>(updatedMatch, HttpStatus.OK);
    }

    @PostMapping("/comment/{matchId}")
    public Comment addComment(@PathVariable Long matchId, @RequestBody String content, @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setMatch(match);
        return commentRepository.save(comment);
    }

    @GetMapping("/comments/{matchId}")
    public List<Comment> getComments(@PathVariable Long matchId, @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist") && !role.equalsIgnoreCase("memberleague")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        return commentRepository.findByMatchId(matchId);
    }


    @PostMapping("/event/{matchId}")
    public Event addEvent(@PathVariable Long matchId, @RequestBody Event event, @RequestParam String role) {
        if (!role.equalsIgnoreCase("journalist")) {
            throw new UnauthorizedException("Role not authorized");
        }
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not found"));
        event.setMatch(match);
        return eventRepository.save(event);
    }

}
