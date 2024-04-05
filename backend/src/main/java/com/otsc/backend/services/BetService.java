package com.otsc.backend.services;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Bet;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.BetMapper;
import com.otsc.backend.repositories.BetRepository;
import com.otsc.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BetService {

    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BetMapper betMapper;

    public static final String ROLE_NONE = "none";
    public static final String ROLE_CREATOR = "creator";
    public static final String ROLE_OPPONENT = "opponent";
    public static final String ROLE_JUDGE = "judge";

    public boolean isBetPresent(Long betId) {
        return betRepository.findById(betId).isPresent();
    }

    public String isUserAllowed(Long betId, String login){
        if(!isBetPresent(betId)){
            return ROLE_NONE;
        }
        if (userRepository.findByLogin(login).isEmpty()){
            return ROLE_NONE;
        }

        BetDto bet = getBetById(betId);
        Long creatorId = bet.getCreator();
        Long opponentId = bet.getOpponent();
        Long judgeId = bet.getJudge();
        UserDto userDto = userService.findByLogin(login);
        if (Objects.equals(userDto.getId(), creatorId)){
            return ROLE_CREATOR;
        } else if (Objects.equals(userDto.getId(), opponentId)){
            return ROLE_OPPONENT;
        } else if (Objects.equals(userDto.getId(), judgeId)){
            return ROLE_JUDGE;
        } else {
            return ROLE_NONE;
        }
    }

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

    public List<BetDto> getBetsByOpponentId(Long opponentId) {
        //TODO: Not final

        if (userRepository.findById(opponentId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        return betMapper.toBetDtos(betRepository.findBetsByOpponent(opponentId).orElse(null));
    }

    public List<BetDto> getBetsByJudgeId(Long judgeId) {
        //TODO: Not final

        if (userRepository.findById(judgeId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        return betMapper.toBetDtos(betRepository.findBetsByJudge(judgeId).orElse(null));
    }

    public List<BetDto> getAllBets() {
        //TODO: Not final

        return betMapper.toBetDtos(betRepository.findAll());
    }

    public BetDto addOpponent(Long betId, String opponent) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        UserDto oppDto = userService.findByLogin(opponent);
        Bet updatedBet = bet.get();
        if (Objects.equals(updatedBet.getJudge(), oppDto.getId())){
            throw new AppException("Opponent and judge cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(updatedBet.getCreator(), oppDto.getId())){
            throw new AppException("Opponent and creator cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(oppDto.getId()).isPresent()) {
            updatedBet.setOpponent(oppDto.getId());
            betRepository.save(updatedBet);
        } else {
            throw new AppException("Opponent not found", HttpStatus.NOT_FOUND);
        }
        return betMapper.toBetDto(updatedBet);
    }

    public BetDto addJudge(Long betId, String judge) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        UserDto judgeDto = userService.findByLogin(judge);
        Bet updatedBet = bet.get();
        if (Objects.equals(updatedBet.getOpponent(), judgeDto.getId())){
            throw new AppException("Judge and opponent cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(updatedBet.getCreator(), judgeDto.getId())){
            throw new AppException("Judge and creator cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(judgeDto.getId()).isPresent()) {
            updatedBet.setJudge(judgeDto.getId());
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
        if (bet.get().getWinner() != null) {
            throw new AppException("Bet already resolved", HttpStatus.FORBIDDEN);
        }

        Bet currentBet = bet.get();
        currentBet.setWinner(winner);
        betRepository.save(currentBet);
        return betMapper.toBetDto(currentBet);
    }

}
