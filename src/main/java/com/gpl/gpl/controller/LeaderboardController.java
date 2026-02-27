package com.gpl.gpl.controller;

import com.gpl.gpl.dto.PlayerStatsDTO;
import com.gpl.gpl.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@CrossOrigin
public class LeaderboardController {

    private final PlayerService playerService;

    @GetMapping("/batting")
    public List<PlayerStatsDTO> getBattingLeaderboard() {
        return playerService.getBattingLeaderboard();
    }

    @GetMapping("/bowling")
    public List<PlayerStatsDTO> getBowlingLeaderboard() {
        return playerService.getBowlingLeaderboard();
    }
}