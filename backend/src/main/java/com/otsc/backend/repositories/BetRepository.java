package com.otsc.backend.repositories;

import com.otsc.backend.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Long> {
    Optional<List<Bet>> findBetsByCreator(Long creator);

    Optional<List<Bet>> findBetsByOpponent(Long opponent);

    Optional<List<Bet>> findBetsByJudge(Long judge);
}
