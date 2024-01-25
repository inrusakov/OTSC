package com.otsc.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_bet")
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator", nullable = false)
    @Size(max = 100)
    private Long creator;

    @Column(name = "opponent")
    @Size(max = 100)
    private Long opponent;

    @Column(name = "judge")
    @Size(max = 100)
    private Long judge;

    @Column(name = "winner")
    @Size(max = 100)
    private Long winner;

    @Column(name = "date", columnDefinition = "DATE")
    @Size(max = 100)
    private LocalDate date;

}
