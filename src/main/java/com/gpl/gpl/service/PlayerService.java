package com.gpl.gpl.service;

import com.gpl.gpl.dto.PlayerStatsDTO;
import com.gpl.gpl.entity.Player;
import com.gpl.gpl.dto.MatchPerformanceDTO;

import java.util.List;

public interface PlayerService {

    Player createPlayer(Player player);

    List<Player> getAllPlayers();

    PlayerStatsDTO getPlayerById(Long id);

    void updatePlayerPerformance(List<MatchPerformanceDTO> performances);
    List<PlayerStatsDTO> getBattingLeaderboard();
List<PlayerStatsDTO> getBowlingLeaderboard();
}