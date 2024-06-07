package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Comment;
import com.supinfo.League_Application.Entity.Event;
import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.repository.CommentRepository;
import com.supinfo.League_Application.repository.EventRepository;
import com.supinfo.League_Application.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/start-match/{id}")
    public Match startMatch(@PathVariable Long id) {
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
        match.setStatus("ongoing");
        return matchRepository.save(match);
    }

    @PostMapping("/end-match/{id}")
    public Match endMatch(@PathVariable Long id) {
        Match match = matchRepository.findById(id).orElseThrow(() -> new RuntimeException("Match not found"));
        match.setStatus("finished");
        return matchRepository.save(match);
    }

    @PostMapping("/comment/{matchId}")
    public Comment addComment(@PathVariable Long matchId, @RequestBody String content) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setMatch(match);
        return commentRepository.save(comment);
    }

    @GetMapping("/comments/{matchId}")
    public List<Comment> getComments(@PathVariable Long matchId) {
        return commentRepository.findByMatchId(matchId);
    }

    @PostMapping("/event/{matchId}")
    public Event addEvent(@PathVariable Long matchId, @RequestBody Event event) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));
        event.setMatch(match);
        return eventRepository.save(event);
    }
}

