package org.example.sip_bk.rest;

import lombok.AllArgsConstructor;
import org.example.sip_bk.entity.Team;
import org.example.sip_bk.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
public class TeamRest {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> saveTeam(@RequestBody Team team) {
        teamService.save(team);
        return ResponseEntity.ok().build();
    }
}
