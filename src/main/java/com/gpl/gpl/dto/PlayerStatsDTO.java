package com.gpl.gpl.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStatsDTO {

    private Long id;
    private String name;

    // Matches
    private Integer totalMatches = 0;

    // Batting
    private Integer totalRuns = 0;
    private Integer totalBallsFaced = 0;
    private Double strikeRate = 0.0;
    private Double battingAverage = 0.0;

    // Bowling
    private Integer totalWickets = 0;
    private Integer totalBallsBowled = 0;
    private Integer totalRunsConceded = 0;
    private Double economy = 0.0;
    private Double bowlingAverage = 0.0;
}