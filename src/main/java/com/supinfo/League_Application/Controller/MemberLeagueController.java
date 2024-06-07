package com.supinfo.League_Application.Controller;

import com.supinfo.League_Application.Entity.Match;
import com.supinfo.League_Application.Repository.IMatchRepository;
import com.supinfo.League_Application.Controller.exception.UnauthorizedException;
import com.supinfo.League_Application.Controller.exception.InvalidMatchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member-league")
public class MemberLeagueController {

    @Autowired
    private IMatchRepository matchRepository;

    @PostMapping("/report-match/{id}")
    public Match reportMatch(@PathVariable Long id, @RequestBody String reason, @RequestParam String role) {
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

    @PostMapping("/suspend-match/{id}")
    public Match suspendMatch(@PathVariable Long id, @RequestBody String reason, @RequestParam String role) {
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
