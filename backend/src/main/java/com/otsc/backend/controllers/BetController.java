package com.otsc.backend.controllers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.services.BetService;
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
public class BetController {

    private final BetService betService;
    private final UserService userService;

    @GetMapping("/allBets")
    public ResponseEntity<List<BetDto>> allBets() {
        //TODO: Not final

        return ResponseEntity.ok(betService.getAllBets());
    }

    @GetMapping("/getBetById")
    public ResponseEntity<BetDto> getBetById(@RequestParam Long betId) {
        //TODO: Not final

        return ResponseEntity.ok(betService.getBetById(betId));
    }

    @GetMapping("/betsByCreatorId")
    public ResponseEntity<List<BetDto>> betsByCreatorId(@RequestParam Long userId) {
        //TODO: Not final

        List<BetDto> bets = betService.getBetsByCreatorId(userId);
        return ResponseEntity.ok(bets);
    }

    @GetMapping("/currentUserBets")
    public ResponseEntity<List<BetDto>> currentUserBets() {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        return ResponseEntity.ok(betService.getBetsByCreatorId(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<BetDto> create(@RequestBody @Valid BetDto betDto) {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        BetDto bet = betService.createBet(betDto, user.getId());
        return ResponseEntity.ok(bet);
    }

    @PostMapping("/setOpponent")
    public ResponseEntity<BetDto> setOpponent(@RequestParam Long betId, @RequestParam Long opponentId) {
        //TODO: Not final

        BetDto bet = betService.addOpponent(betId, opponentId);
        return ResponseEntity.ok(bet);
    }

    @PostMapping("/setJudge")
    public ResponseEntity<BetDto> setJudge(@RequestParam Long betId, @RequestParam Long judgeId) {
        //TODO: Not final

        BetDto bet = betService.addJudge(betId, judgeId);
        return ResponseEntity.ok(bet);
    }

    @PostMapping("/resolve")
    public ResponseEntity<BetDto> resolve(@RequestParam Long betId, @RequestParam Long winner) {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        BetDto bet = betService.resolveBet(betId, winner, user.getId());
        return ResponseEntity.ok(bet);
    }
}
