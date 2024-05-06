package com.otsc.backend.repositories;

import com.otsc.backend.entities.ContestBetRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContestBetRefRepository extends JpaRepository<ContestBetRef, String> {
    Optional<List<ContestBetRef>> findContestBetRefByContest(String contestId);
}
