package com.otsc.backend.services;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.entities.Bet;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.BetMapper;
import com.otsc.backend.repositories.BetRepository;
import com.otsc.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BetService {

    private final BetRepository betRepository;

    private final UserRepository userRepository;

    private final BetMapper betMapper;

    public void createBet(BetDto betDto, Long creatorId) {
        //TODO: Not final

        betDto.setCreator(creatorId);
        Bet bet = betMapper.betDtoToBet(betDto);

        betRepository.save(bet);
    }

    public List<Bet> getBets(Long creatorId){
        //TODO: Not final

        if (userRepository.findById(creatorId).isEmpty()){
            throw new AppException("No creator found", HttpStatus.NOT_FOUND);
        }

        return betRepository.findBetsByCreator(creatorId).orElse(null);
    }

    public void addOpponent(Long betId, Long opponentId) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()){
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        if (userRepository.findById(opponentId).isPresent()){
            Bet updatedBet = bet.get();
            updatedBet.setOpponent(opponentId);
            betRepository.save(updatedBet);
        } else {
            throw new AppException("Opponent not found", HttpStatus.NOT_FOUND);
        }
    }

    public void addJudge(Long betId, Long judgeId) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()){
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        if (userRepository.findById(judgeId).isPresent()){
            Bet updatedBet = bet.get();
            updatedBet.setJudge(judgeId);
            betRepository.save(updatedBet);
        } else {
            throw new AppException("Judge not found", HttpStatus.NOT_FOUND);
        }
    }

    public void resolveBet(Long betId, Long winner) {
        //TODO: Create bet resolving logic.
    }

}
