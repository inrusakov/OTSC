package com.otsc.backend.services;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.ContestDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Bet;
import com.otsc.backend.entities.Contest;
import com.otsc.backend.entities.ContestBetRef;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.BetMapper;
import com.otsc.backend.mappers.ContestMapper;
import com.otsc.backend.repositories.BetRepository;
import com.otsc.backend.repositories.ContestBetRefRepository;
import com.otsc.backend.repositories.ContestRepository;
import com.otsc.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContestService {

    private final ContestRepository contestRepository;
    private final ContestBetRefRepository contestBetRefRepository;
    private final BetRepository betRepository;
    private final ContestMapper contestMapper;
    private final BetService betService;
    private final BetMapper betMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public static final String ROLE_NONE = "none";
    public static final String ROLE_CREATOR = "creator";
    public static final String ROLE_OPPONENT = "opponent";
    public static final String ROLE_JUDGE = "judge";
    public static final String DRAW_RESULT = "draw";

    public boolean isContestPresent(String contestId) {
        return contestRepository.findById(contestId).isPresent();
    }

    public String isUserAllowed(String contestId, String login) {
        if (!isContestPresent(contestId)) {
            return ROLE_NONE;
        }
        if (userRepository.findByLogin(login).isEmpty()) {
            return ROLE_NONE;
        }

        ContestDto contestDto = getContestById(contestId);
        String creatorId = contestDto.getCreator();
        String opponentId = contestDto.getOpponent();
        String judgeId = contestDto.getJudge();

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

    public ContestDto getContestById(String contestId) {
        if (contestRepository.findById(contestId).isEmpty()) {
            throw new AppException("No contest found", HttpStatus.NOT_FOUND);
        }
        ContestDto contestDto = contestMapper.toContestDto(contestRepository.findContestById(contestId).get());
        Optional<List<ContestBetRef>> contestBetRefByContest = contestBetRefRepository.findContestBetRefByContest(contestId);
        Map<Integer, BetDto> contestBets = new HashMap<>();
        if (contestBetRefByContest.isPresent()){
            for (ContestBetRef contestBetRef : contestBetRefByContest.get()) {
                contestBets.put(contestBetRef.getBetOrder(), betService.getBetById(contestBetRef.getBet()));
            }
        }
        contestDto.setBetMap(contestBets);
        contestDto.setCurrentSize(contestBets.size());

        if(Objects.equals(contestDto.getSize(), contestDto.getCurrentSize()) && contestDto.getWinner() == null){
            int creatorWon = 0;
            int opponentWon = 0;
            for (BetDto value : contestDto.getBetMap().values()) {
                if (Objects.equals(value.getWinner(), value.getCreator())){
                    creatorWon++;
                } else if (Objects.equals(value.getWinner(), value.getOpponent())){
                    opponentWon++;
                }
            }
            if (creatorWon> opponentWon){
                contestDto.setWinner(contestDto.getCreator());
            } else if (opponentWon > creatorWon){
                contestDto.setWinner(contestDto.getOpponent());
            } else {
                contestDto.setWinner(DRAW_RESULT);
            }
            contestRepository.save(contestMapper.contestDtoToContest(contestDto));

        }

        return contestDto;
    }

    public List<ContestDto> getContestsByCreatorId(String creatorId) {
        List<Contest> contestsByCreator = contestRepository.findContestByCreator(creatorId).orElse(new ArrayList<>());
        List<Contest> result = new ArrayList<>();
        for (Contest contest : contestsByCreator) {
            if (contest.getWinner() == null) {
                result.add(contest);
            }
        }

        return contestMapper.toContestDtos(result);
    }

    public List<ContestDto> getContestsByOpponentId(String opponentId) {
        List<Contest> contestsByOpponent = contestRepository.findContestsByOpponent(opponentId).orElse(new ArrayList<>());
        List<Contest> result = new ArrayList<>();
        for (Contest contest : contestsByOpponent) {
            if (contest.getWinner() == null) {
                result.add(contest);
            }
        }

        return contestMapper.toContestDtos(result);
    }

    public List<ContestDto> getContestsByJudgeId(String judgeId) {
        List<Contest> contestsByJudge = contestRepository.findContestsByJudge(judgeId).orElse(new ArrayList<>());
        List<Contest> result = new ArrayList<>();
        for (Contest contest : contestsByJudge) {
            if (contest.getWinner() == null) {
                result.add(contest);
            }
        }

        return contestMapper.toContestDtos(result);
    }

    public ContestDto createContest(ContestDto contestDto, String creatorId) {
        contestDto.setCreator(creatorId);
        Contest contest = contestMapper.contestDtoToContest(contestDto);

        contestRepository.save(contest);
        return contestMapper.toContestDto(contest);
    }

    public BetDto addBetToContest(BetDto betDto, String user) {
        Bet bet = betMapper.betDtoToBet(betDto);
        bet.setCreator(user);
        bet.setInContest(true);
        ContestDto contest = getContestById(betDto.getContest());
        if (contest.getOpponent() != null){
            bet.setOpponent(contest.getOpponent());
        }
        if (contest.getJudge() != null){
            bet.setJudge(contest.getJudge());
        }

        Bet savedBet = betRepository.save(bet);
        Contest savedContest = contestRepository.save(contestMapper.contestDtoToContest(contest));

        ContestBetRef contestBetRef = new ContestBetRef();
        contestBetRef.setContest(savedContest.getId());
        contestBetRef.setBet(savedBet.getId());
        contestBetRef.setBetOrder(contest.getBetMap().size());

        contestBetRefRepository.save(contestBetRef);

        return betMapper.toBetDto(bet);
    }

    public ContestDto addOpponent(String contestId, String opponent) {
        ContestDto contestDto = getContestById(contestId);

        UserDto oppDto = userService.findByLogin(opponent);
        if (Objects.equals(contestDto.getJudge(), oppDto.getId())) {
            throw new AppException("Opponent and judge cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(contestDto.getCreator(), oppDto.getId())) {
            throw new AppException("Opponent and creator cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(oppDto.getId()).isPresent()) {
            contestDto.setOpponent(oppDto.getId());
            contestRepository.save(contestMapper.contestDtoToContest(contestDto));
        } else {
            throw new AppException("Opponent not found", HttpStatus.NOT_FOUND);
        }
        return contestDto;
    }

    public ContestDto addJudge(String contestId, String judge) {
        ContestDto contestDto = getContestById(contestId);

        UserDto judgeDto = userService.findByLogin(judge);
        if (Objects.equals(contestDto.getOpponent(), judgeDto.getId())) {
            throw new AppException("Judge and opponent cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (Objects.equals(contestDto.getCreator(), judgeDto.getId())) {
            throw new AppException("Judge and creator cannot be same users", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findById(judgeDto.getId()).isPresent()) {
            contestDto.setJudge(judgeDto.getId());
            contestRepository.save(contestMapper.contestDtoToContest(contestDto));
        } else {
            throw new AppException("Judge not found", HttpStatus.NOT_FOUND);
        }
        return contestDto;
    }

    public List<ContestDto> getResolvedContests(String user) {
        UserDto userDto = userService.findByLogin(user);

        List<Contest> aggregatedList = contestRepository.findContestByCreator(userDto.getId()).orElse(new ArrayList<>());
        aggregatedList.addAll(contestRepository.findContestsByOpponent(userDto.getId()).orElse(new ArrayList<>()));
        aggregatedList.addAll(contestRepository.findContestsByJudge(userDto.getId()).orElse(new ArrayList<>()));

        List<Contest> result = new ArrayList<>();
        for (Contest contest : aggregatedList) {
            if (contest.getWinner() != null){
                result.add(contest);
            }
        }

        return contestMapper.toContestDtos(result);
    }
}
