package com.gpl.gpl.controller;

import com.gpl.gpl.dto.PlayerStatsDTO;
import com.gpl.gpl.entity.Player;
import com.gpl.gpl.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.gpl.gpl.dto.MatchPerformanceDTO;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
@CrossOrigin
public class PlayerController {

    private final PlayerService playerService;

    // Create Player
    @PostMapping
    public Player create(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    // Get All Players
    @GetMapping
    public List<Player> getAll() {
        return playerService.getAllPlayers();
    }

    // Get Player Full Details
    @GetMapping("/{id}")
    public PlayerStatsDTO getById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    // Update Performance After Match
    @PostMapping("/performance")
    public String updatePerformance(
            @RequestBody List<MatchPerformanceDTO> performances) {

        playerService.updatePlayerPerformance(performances);
        return "Player stats updated successfully";
    }
}