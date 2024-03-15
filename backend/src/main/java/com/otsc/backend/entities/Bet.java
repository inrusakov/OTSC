package com.otsc.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "creator")
    private Long creator;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "opponent")
    private Long opponent;

    @Column(name = "judge")
    private Long judge;

    @Column(name = "winner")
    private Long winner;

}
