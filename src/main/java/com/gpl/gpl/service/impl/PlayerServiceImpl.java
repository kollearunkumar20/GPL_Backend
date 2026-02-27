package com.gpl.gpl.service.impl;

import com.gpl.gpl.dto.PlayerStatsDTO;
import com.gpl.gpl.dto.MatchPerformanceDTO;
import com.gpl.gpl.entity.Player;
import com.gpl.gpl.repository.PlayerRepository;
import com.gpl.gpl.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository repository;

    // -----------------------------
    // Create Player
    // -----------------------------
    @Override
    public Player createPlayer(Player player) {
        return repository.save(player);
    }

    // -----------------------------
    // Get All Players
    // -----------------------------
    @Override
    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    // -----------------------------
    // Get Single Player
    // -----------------------------
@Override
public PlayerStatsDTO getPlayerById(Long id) {

    Player player = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Player not found"));

    int runs = player.getTotalRuns() != null ? player.getTotalRuns() : 0;
    int ballsFaced = player.getTotalBallsFaced() != null ? player.getTotalBallsFaced() : 0;
    int wickets = player.getTotalWickets() != null ? player.getTotalWickets() : 0;
    int ballsBowled = player.getTotalBallsBowled() != null ? player.getTotalBallsBowled() : 0;
    int runsConceded = player.getTotalRunsConceded() != null ? player.getTotalRunsConceded() : 0;
    int innings = player.getTotalInnings() != null ? player.getTotalInnings() : 0;
    int notOuts = player.getTotalNotOuts() != null ? player.getTotalNotOuts() : 0;

    double strikeRate = 0;
    if (ballsFaced > 0) {
        strikeRate = (runs * 100.0) / ballsFaced;
    }

    double battingAverage = 0;
    int dismissals = innings - notOuts;
    if (dismissals > 0) {
        battingAverage = runs * 1.0 / dismissals;
    }

    double economy = 0;
    if (ballsBowled > 0) {
        economy = (runsConceded * 6.0) / ballsBowled;
    }

    double bowlingAverage = 0;
    if (wickets > 0) {
        bowlingAverage = runsConceded * 1.0 / wickets;
    }

    return PlayerStatsDTO.builder()
            .id(player.getId())
            .name(player.getName())
            .totalMatches(innings)
            .totalRuns(runs)
            .totalBallsFaced(ballsFaced)
            .strikeRate(strikeRate)
            .battingAverage(battingAverage)
            .totalWickets(wickets)
            .totalBallsBowled(ballsBowled)
            .totalRunsConceded(runsConceded)
            .economy(economy)
            .bowlingAverage(bowlingAverage)
            .build();
}

    // -----------------------------
    // Update Match Performance
    // -----------------------------
    @Override
    public void updatePlayerPerformance(List<MatchPerformanceDTO> performances) {

        for (MatchPerformanceDTO dto : performances) {

            Player player = repository.findById(dto.getPlayerId())
                    .orElseThrow(() -> new RuntimeException("Player not found"));

            int runs = safe(dto.getRuns());
            int ballsFaced = safe(dto.getBallsFaced());
            int ballsBowled = safe(dto.getBallsBowled());
            int runsConceded = safe(dto.getRunsConceded());
            int wickets = safe(dto.getWickets());

            // Batting
            player.setTotalRuns(safe(player.getTotalRuns()) + runs);
            player.setTotalBallsFaced(safe(player.getTotalBallsFaced()) + ballsFaced);

            if (ballsFaced > 0) {
                player.setTotalInnings(safe(player.getTotalInnings()) + 1);
            }

            if (Boolean.FALSE.equals(dto.getOut()) && ballsFaced > 0) {
                player.setTotalNotOuts(safe(player.getTotalNotOuts()) + 1);
            }

            // Bowling
            player.setTotalBallsBowled(safe(player.getTotalBallsBowled()) + ballsBowled);
            player.setTotalRunsConceded(safe(player.getTotalRunsConceded()) + runsConceded);
            player.setTotalWickets(safe(player.getTotalWickets()) + wickets);

            repository.save(player);
        }
    }

    // -----------------------------
    // Batting Leaderboard
    // -----------------------------
    @Override
    public List<PlayerStatsDTO> getBattingLeaderboard() {

        return repository.findAll().stream()
                .map(player -> {

                    int runs = safe(player.getTotalRuns());
                    int balls = safe(player.getTotalBallsFaced());
                    int innings = safe(player.getTotalInnings());
                    int notOuts = safe(player.getTotalNotOuts());

                    double strikeRate = balls > 0
                            ? (runs * 100.0) / balls
                            : 0;

                    int dismissals = innings - notOuts;

                    double average = dismissals > 0
                            ? (runs * 1.0) / dismissals
                            : 0;

                    return PlayerStatsDTO.builder()
                            .id(player.getId())
                            .name(player.getName())
                            .totalRuns(runs)
                            .strikeRate(strikeRate)
                            .battingAverage(average)
                            .totalMatches(innings)
                            .build();
                })
                .sorted((a, b) -> Integer.compare(
                        safe(b.getTotalRuns()),
                        safe(a.getTotalRuns())
                ))
                .toList();
    }

    // -----------------------------
    // Bowling Leaderboard
    // -----------------------------
    @Override
    public List<PlayerStatsDTO> getBowlingLeaderboard() {

        return repository.findAll().stream()
                .map(player -> {

                    int balls = safe(player.getTotalBallsBowled());
                    int runsConceded = safe(player.getTotalRunsConceded());
                    int wickets = safe(player.getTotalWickets());
                    int matches = safe(player.getTotalInnings());

                    double economy = balls > 0
                            ? (runsConceded * 6.0) / balls
                            : 0;

                    double average = wickets > 0
                            ? (runsConceded * 1.0) / wickets
                            : 0;

                    return PlayerStatsDTO.builder()
                            .id(player.getId())
                            .name(player.getName())
                            .totalWickets(wickets)
                            .economy(economy)
                            .bowlingAverage(average)
                            .totalMatches(matches)
                            .build();
                })
                .sorted((a, b) -> Integer.compare(
                        safe(b.getTotalWickets()),
                        safe(a.getTotalWickets())
                ))
                .toList();
    }

    // -----------------------------
    // Null Safety Helper
    // -----------------------------
    private int safe(Integer value) {
        return value == null ? 0 : value;
    }
}