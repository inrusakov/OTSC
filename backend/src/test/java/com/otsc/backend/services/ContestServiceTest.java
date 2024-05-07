package com.otsc.backend.services;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.ContestDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Contest;
import com.otsc.backend.entities.ContestBetRef;
import com.otsc.backend.entities.User;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.BetMapper;
import com.otsc.backend.mappers.ContestMapper;
import com.otsc.backend.mappers.UserMapper;
import com.otsc.backend.repositories.BetRepository;
import com.otsc.backend.repositories.ContestBetRefRepository;
import com.otsc.backend.repositories.ContestRepository;
import com.otsc.backend.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContestServiceTest {
    @Mock
    private ContestRepository contestRepository;
    @Mock
    private ContestBetRefRepository contestBetRefRepository;
    @Mock
    private BetRepository betRepository;
    private ContestMapper contestMapper = Mappers.getMapper(ContestMapper.class);
    @Mock
    private BetService betService;
    private BetMapper betMapper = Mappers.getMapper(BetMapper.class);
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testIsContestPresent_ExistingContest() {
        String contestId = "123";

        // Mock behavior for contestRepository.findById
        when(contestRepository.findById(contestId)).thenReturn(Optional.of(new Contest()));

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        boolean isPresent = contestService.isContestPresent(contestId);

        assertTrue(isPresent);
    }

    @Test
    public void testIsContestPresent_NonexistentContest() {
        String contestId = "456";

        // Mock behavior for contestRepository.findById
        when(contestRepository.findById(contestId)).thenReturn(Optional.empty());

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        boolean isPresent = contestService.isContestPresent(contestId);

        assertFalse(isPresent);
    }

    @Test(expected = AppException.class)
    public void testGetContestById_NonexistentContest() {
        String contestId = "nonexistentContest";

        // Mock behavior for contestRepository.findById
        when(contestRepository.findById(contestId)).thenReturn(Optional.empty());

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        contestService.getContestById(contestId);
    }

    @Test
    public void testGetContestsByCreatorId() {
        String creatorId = "123";

        // Mock behavior for contestRepository.findContestByCreator
        List<Contest> contests = new ArrayList<>();
        contests.add(new Contest());
        when(contestRepository.findContestByCreator(creatorId)).thenReturn(Optional.of(contests));

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        List<ContestDto> retrievedContests = contestService.getContestsByCreatorId(creatorId);

        assertEquals(1, retrievedContests.size());
    }

    @Test
    public void testGetContestsByOpponentId() {
        String opponentId = "456";

        // Mock behavior for contestRepository.findContestsByOpponent
        List<Contest> contests = new ArrayList<>();
        contests.add(new Contest());
        when(contestRepository.findContestsByOpponent(opponentId)).thenReturn(Optional.of(contests));

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        List<ContestDto> retrievedContests = contestService.getContestsByOpponentId(opponentId);

        assertEquals(1, retrievedContests.size());
    }

    @Test
    public void testGetContestsByJudgeId() {
        String judgeId = "789";

        // Mock behavior for contestRepository.findContestsByJudge
        List<Contest> contests = new ArrayList<>();
        contests.add(new Contest());
        when(contestRepository.findContestsByJudge(judgeId)).thenReturn(Optional.of(contests));

        ContestService contestService = new ContestService(contestRepository, contestBetRefRepository, betRepository, contestMapper, betService, betMapper, userRepository, userService);

        List<ContestDto> retrievedContests = contestService.getContestsByJudgeId(judgeId);

        assertEquals(1, retrievedContests.size());
    }

}