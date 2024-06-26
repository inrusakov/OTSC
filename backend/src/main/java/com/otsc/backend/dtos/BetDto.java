package com.otsc.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetDto {

    private String id;
    private String creator;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private String opponent;
    private String judge;
    private String winner;
    private String contest;
    private boolean inContest;
}
