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

    public BetDto createBet(BetDto betDto, Long creatorId) {
        //TODO: Not final

        betDto.setCreator(creatorId);
        Bet bet = betMapper.betDtoToBet(betDto);

        betRepository.save(bet);
        return betMapper.toBetDto(bet);
    }

    public BetDto getBetById(Long betId) {
        //TODO: Not final

        if (betRepository.findById(betId).isEmpty()) {
            throw new AppException("No bet found", HttpStatus.NOT_FOUND);
        }

        return betMapper.toBetDto(betRepository.findBetById(betId).orElse(null));
    }

    public List<BetDto> getBetsByCreatorId(Long creatorId) {
        //TODO: Not final

        if (userRepository.findById(creatorId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        return betMapper.toBetDtos(betRepository.findBetsByCreator(creatorId).orElse(null));
    }

    public List<BetDto> getAllBets() {
        //TODO: Not final

        return betMapper.toBetDtos(betRepository.findAll());
    }

    public BetDto addOpponent(Long betId, Long opponentId) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        Bet updatedBet = bet.get();
        if (userRepository.findById(opponentId).isPresent()) {
            updatedBet.setOpponent(opponentId);
            betRepository.save(updatedBet);
        } else {
            throw new AppException("Opponent not found", HttpStatus.NOT_FOUND);
        }
        return betMapper.toBetDto(updatedBet);
    }

    public BetDto addJudge(Long betId, Long judgeId) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        Bet updatedBet = bet.get();
        if (userRepository.findById(judgeId).isPresent()) {
            updatedBet.setJudge(judgeId);
            betRepository.save(updatedBet);
        } else {
            throw new AppException("Judge not found", HttpStatus.NOT_FOUND);
        }
        return betMapper.toBetDto(updatedBet);
    }

    public BetDto resolveBet(Long betId, Long winner, Long currentOp) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        if (userRepository.findById(winner).isEmpty()) {
            throw new AppException("Winner not found", HttpStatus.NOT_FOUND);
        }
        if (userRepository.findById(currentOp).isEmpty()) {
            throw new AppException("Current user not found", HttpStatus.NOT_FOUND);
        }
        if (!bet.get().getJudge().equals(currentOp)) {
            throw new AppException("User not allowed to resolve bet", HttpStatus.FORBIDDEN);
        }
        if (!bet.get().getWinner().equals(0L)) {
            throw new AppException("Bet already resolved", HttpStatus.FORBIDDEN);
        }

        Bet currentBet = bet.get();
        currentBet.setWinner(winner);
        betRepository.save(currentBet);
        return betMapper.toBetDto(currentBet);
    }

}
