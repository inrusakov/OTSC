package com.otsc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetDto {

    private String id;
    private String creator;
    private String title;
    private String description;
    private LocalDate date;
    private String opponent;
    private String judge;
    private String winner;
}
