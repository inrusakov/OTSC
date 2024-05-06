package com.otsc.backend.repositories;

import com.otsc.backend.entities.Bet;
import com.otsc.backend.entities.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestRepository extends JpaRepository<Contest, String> {
    Optional<Contest> findContestById(String id);

    Optional<List<Contest>> findContestByCreator(String creator);

    Optional<List<Contest>> findContestsByOpponent(String opponent);

    Optional<List<Contest>> findContestsByJudge(String judge);
}
