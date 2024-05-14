package com.otsc.backend.controllers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.ContestDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.services.BetService;
import com.otsc.backend.services.ContestService;
import com.otsc.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContestController {

    private final ContestService contestService;
    private final UserService userService;

    @PostMapping("/createContest")
    public ResponseEntity<ContestDto> create(@RequestBody @Valid ContestDto contestDto) {

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        ContestDto contest = contestService.createContest(contestDto, user.getId());
        return ResponseEntity.ok(contest);
    }

    @GetMapping("/getContestById")
    public ResponseEntity<ContestDto> getContestById(@RequestParam String contestId) {
        //TODO: Not final

        return ResponseEntity.ok(contestService.getContestById(contestId));
    }

    @GetMapping("/getContestRole")
    public ResponseEntity<String> isUserAllowed(@RequestParam String contestId) {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        return ResponseEntity.ok(contestService.isUserAllowed(contestId, user.getLogin()));
    }

    @GetMapping("/myCreatedContests")
    public ResponseEntity<List<ContestDto>> myCreatedContests() {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        return ResponseEntity.ok(contestService.getContestsByCreatorId(user.getId()));
    }

    @GetMapping("/myOpponentContests")
    public ResponseEntity<List<ContestDto>> myOpponentContests() {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        return ResponseEntity.ok(contestService.getContestsByOpponentId(user.getId()));
    }

    @GetMapping("/myJudgeContests")
    public ResponseEntity<List<ContestDto>> myJudgeContests() {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        return ResponseEntity.ok(contestService.getContestsByJudgeId(user.getId()));
    }

    @PostMapping("/addBetToContest")
    public ResponseEntity<BetDto> addBetToContest(@RequestBody @Valid BetDto betDto) {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());

        return ResponseEntity.ok(contestService.addBetToContest(betDto, user.getId()));
    }

    @PostMapping("/setContestOpponent")
    public ResponseEntity<ContestDto> setOpponent(@RequestParam String contestId, @RequestParam String opponent) {
        //TODO: Not final

        ContestDto contestDto = contestService.addOpponent(contestId, opponent);
        return ResponseEntity.ok(contestDto);
    }

    @PostMapping("/setContestJudge")
    public ResponseEntity<ContestDto> setJudge(@RequestParam String contestId, @RequestParam String judge) {
        //TODO: Not final

        ContestDto contestDto = contestService.addJudge(contestId, judge);
        return ResponseEntity.ok(contestDto);
    }

    @GetMapping("/contestHistory")
    public ResponseEntity<List<ContestDto>> getHistory() {
        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        return ResponseEntity.ok(contestService.getResolvedContests(user.getLogin()));
    }
}
