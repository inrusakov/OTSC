package com.otsc.backend.controllers;

import com.otsc.backend.dtos.BetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BetController {

    @GetMapping("/bet")
    public ResponseEntity<List<BetDto>> bets() {
        return ResponseEntity.ok(List.of(new BetDto()));
    }
}
