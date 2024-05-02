package com.otsc.backend.repositories;

import com.otsc.backend.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, String> {
    Optional<Bet> findBetById(String id);

    Optional<List<Bet>> findBetsByCreator(String creator);

    Optional<List<Bet>> findBetsByOpponent(String opponent);

    Optional<List<Bet>> findBetsByJudge(String judge);

}
