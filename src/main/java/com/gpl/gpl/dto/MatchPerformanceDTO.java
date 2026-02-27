package com.gpl.gpl.dto;

import lombok.Data;

@Data
public class MatchPerformanceDTO {

    private Long playerId;

    private Integer runs = 0;
    private Integer ballsFaced = 0;
    private Boolean out = false;

    private Integer ballsBowled = 0;
    private Integer runsConceded = 0;
    private Integer wickets = 0;
}