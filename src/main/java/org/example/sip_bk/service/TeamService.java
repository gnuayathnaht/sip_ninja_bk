package org.example.sip_bk.service;

import lombok.AllArgsConstructor;
import org.example.sip_bk.dao.TeamDao;
import org.example.sip_bk.entity.Team;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamDao teamDao;

    public Team save(Team team) {
        return teamDao.save(team);
    }

    public List<Team> findAll() { return teamDao.findAll(); }

    public Team findTeamById(Long id) {
        return teamDao.findById(id).orElse(null);
    }
}
