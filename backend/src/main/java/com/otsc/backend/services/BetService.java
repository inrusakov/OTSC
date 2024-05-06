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

import java.util.ArrayList;
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

    public boolean isBetPresent(String betId) {
        return betRepository.findById(betId).isPresent();
    }

    public String isUserAllowed(String betId, String login) {
        if (!isBetPresent(betId)) {
            return ROLE_NONE;
        }
        if (userRepository.findByLogin(login).isEmpty()) {
            return ROLE_NONE;
        }

        BetDto bet = getBetById(betId);
        String creatorId = bet.getCreator();
        String opponentId = bet.getOpponent();
        String judgeId = bet.getJudge();
        UserDto userDto = userService.findByLogin(login);
        if (Objects.equals(userDto.getId(), creatorId)) {
            return ROLE_CREATOR;
        } else if (Objects.equals(userDto.getId(), opponentId)) {
            return ROLE_OPPONENT;
        } else if (Objects.equals(userDto.getId(), judgeId)) {
            return ROLE_JUDGE;
        } else {
            return ROLE_NONE;
        }
    }

    public BetDto createBet(BetDto betDto, String creatorId) {
        //TODO: Not final

        betDto.setCreator(creatorId);
        Bet bet = betMapper.betDtoToBet(betDto);

        betRepository.save(bet);
        return betMapper.toBetDto(bet);
    }

    public BetDto getBetById(String betId) {
        //TODO: Not final

        if (betRepository.findById(betId).isEmpty()) {
            throw new AppException("No bet found", HttpStatus.NOT_FOUND);
        }

        return betMapper.toBetDto(betRepository.findBetById(betId).orElse(null));
    }

    public List<BetDto> getBetsByCreatorId(String creatorId) {
        //TODO: Not final

        if (userRepository.findById(creatorId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        List<Bet> betsByCreator = betRepository.findBetsByCreator(creatorId).orElse(new ArrayList<>());
        List<Bet> result = new ArrayList<>();
        for (Bet bet : betsByCreator) {
            if (bet.getWinner() == null && !bet.isInContest()) {
                result.add(bet);
            }
        }

        return betMapper.toBetDtos(result);
    }

    public List<BetDto> getBetsByOpponentId(String opponentId) {
        //TODO: Not final

        if (userRepository.findById(opponentId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        List<Bet> betsByOpponent = betRepository.findBetsByOpponent(opponentId).orElse(new ArrayList<>());
        List<Bet> result = new ArrayList<>();
        for (Bet bet : betsByOpponent) {
            if (bet.getWinner() == null && !bet.isInContest()) {
                result.add(bet);
            }
        }

        return betMapper.toBetDtos(result);
    }

    public List<BetDto> getBetsByJudgeId(String judgeId) {
        //TODO: Not final

        if (userRepository.findById(judgeId).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }

        List<Bet> betsByOpponent = betRepository.findBetsByJudge(judgeId).orElse(new ArrayList<>());
        List<Bet> result = new ArrayList<>();
        for (Bet bet : betsByOpponent ) {
            if (bet.getWinner() == null && !bet.isInContest()) {
                result.add(bet);
            }
        }

        return betMapper.toBetDtos(result);
    }

    public List<BetDto> getAllBets() {
        //TODO: Not final

        return betMapper.toBetDtos(betRepository.findAll());
    }

    public List<BetDto> getResolvedBets(String user) {
        if (userRepository.findByLogin(user).isEmpty()) {
            throw new AppException("No user found", HttpStatus.NOT_FOUND);
        }
        UserDto userDto = userService.findByLogin(user);

        List<Bet> aggregatedList = betRepository.findBetsByCreator(userDto.getId()).orElse(new ArrayList<>());
        aggregatedList.addAll(betRepository.findBetsByOpponent(userDto.getId()).orElse(new ArrayList<>()));
        aggregatedList.addAll(betRepository.findBetsByJudge(userDto.getId()).orElse(new ArrayList<>()));

        List<Bet> result = new ArrayList<>();
        for (Bet bet : aggregatedList) {
            if (bet.getWinner() != null && !bet.isInContest()){
                result.add(bet);
            }
        }

        return betMapper.toBetDtos(result);
    }

    public BetDto addOpponent(String betId, String opponent) {
        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        UserDto oppDto = userService.findByLogin(opponent);
        Bet updatedBet = bet.get();
        if (Objects.equals(updatedBet.getJudge(), oppDto.getId())) {
            throw new AppException("Opponent and judge cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(updatedBet.getCreator(), oppDto.getId())) {
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

    public BetDto addJudge(String betId, String judge) {
        //TODO: Not final

        Optional<Bet> bet = betRepository.findBetById(betId);
        if (bet.isEmpty()) {
            throw new AppException("Bet not found", HttpStatus.NOT_FOUND);
        }
        UserDto judgeDto = userService.findByLogin(judge);
        Bet updatedBet = bet.get();
        if (Objects.equals(updatedBet.getOpponent(), judgeDto.getId())) {
            throw new AppException("Judge and opponent cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(updatedBet.getCreator(), judgeDto.getId())) {
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

    public BetDto resolveBet(String betId, String winner, String currentOp) {
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
