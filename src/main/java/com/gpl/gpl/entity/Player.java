package com.gpl.gpl.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role;

    // Batting
    private Integer totalRuns = 0;
    private Integer totalBallsFaced = 0;
    private Integer totalInnings = 0;
    private Integer totalNotOuts = 0;

    // Bowling
    private Integer totalBallsBowled = 0;
    private Integer totalRunsConceded = 0;
    private Integer totalWickets = 0;
}