package org.example.sip_bk.dao;

import lombok.extern.java.Log;
import org.example.sip_bk.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDao extends JpaRepository<Team, Long> {
}
