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

    private Long id;
    private Long creator;
    private Long opponent;
    private Long judge;
    private Long winner;
    private LocalDate date;
}
