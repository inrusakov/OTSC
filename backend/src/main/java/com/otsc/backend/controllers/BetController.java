package com.otsc.backend.controllers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.mappers.BetMapper;
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
    private final BetMapper betMapper;
    private final UserService userService;

    @GetMapping("/bets")
    public ResponseEntity<List<BetDto>> bets() {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        List<BetDto> bets = betMapper.toBetDtos(betService.getBets(user.getId()));
        return ResponseEntity.ok(bets);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid BetDto betDto) {
        //TODO: Not final

        UserDto principal = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.findByLogin(principal.getLogin());
        betService.createBet(betDto, user.getId());
        return ResponseEntity.ok("Bet Created");
    }

    @PostMapping("/setOpponent")
    public ResponseEntity<String> setOpponent(@RequestParam Long betId, @RequestParam Long opponentId) {
        //TODO: Not final

        betService.addOpponent(betId, opponentId);
        return ResponseEntity.ok("Bet Created");
    }

    @PostMapping("/setJudge")
    public ResponseEntity<String> setJudge(@RequestParam Long betId, @RequestParam Long judgeId) {
        //TODO: Not final

        betService.addJudge(betId, judgeId);
        return ResponseEntity.ok("Bet Created");
    }

    @PostMapping("/resolve")
    public ResponseEntity<String> resolve(@RequestParam Long betId, @RequestParam Long winner) {
        //TODO: Not final

        betService.resolveBet(betId, winner);
        return ResponseEntity.ok("Bet Created");
    }
}
