package com.gpl.gpl.repository;

import com.gpl.gpl.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}