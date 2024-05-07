package com.otsc.backend.services;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.UserDto;
import com.otsc.backend.entities.Bet;
import com.otsc.backend.entities.User;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.mappers.BetMapper;
import com.otsc.backend.mappers.UserMapper;
import com.otsc.backend.repositories.BetRepository;
import com.otsc.backend.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BetServiceTest {

    @Mock
    private BetRepository betRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private BetMapper betMapper;
    @Mock
    private UserMapper userMapper;

    private BetService betService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        betService = new BetService(betRepository, userRepository, userService, betMapper);
    }

    @Test
    public void testIsBetPresent_ExistingBet() {
        String existingBetId = "123";
        when(betRepository.findById(existingBetId)).thenReturn(Optional.of(new Bet()));

        assertTrue(betService.isBetPresent(existingBetId));
    }

    @Test
    public void testIsBetPresent_NonexistentBet() {
        String nonexistentBetId = "456";
        when(betRepository.findById(nonexistentBetId)).thenReturn(Optional.empty());

        assertFalse(betService.isBetPresent(nonexistentBetId));
    }

    @Test
    public void testIsUserAllowed_ExistingBet_Creator() {
        String betId = "123";
        String login = "creatorLogin";
        String creatorId = "creatorId";

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setId(creatorId);

        BetDto betDto = new BetDto();
        betDto.setCreator(creatorId);
        betDto.setId(betId);

        Bet bet = new Bet();
        bet.setId(betId);
        bet.setJudge(creatorId);

        when(betRepository.findById(betId)).thenReturn(Optional.of(bet));
        when(betService.getBetById(betId)).thenReturn(betDto);
        when(userService.findByLogin(login)).thenReturn(userDto);

        String role = betService.isUserAllowed(betId, login);

        assertEquals(BetService.ROLE_CREATOR, role);
    }

    @Test
    public void testIsUserAllowed_ExistingBet_Opponent() {
        String betId = "123";
        String login = "opponentLogin";
        String opponentId = "opponentId";

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setId(opponentId);

        BetDto betDto = new BetDto();
        betDto.setOpponent(opponentId);
        betDto.setId(betId);

        Bet bet = new Bet();
        bet.setId(betId);
        bet.setJudge(opponentId);

        when(betRepository.findById(betId)).thenReturn(Optional.of(bet));
        when(betService.getBetById(betId)).thenReturn(betDto);
        when(userService.findByLogin(login)).thenReturn(userDto);

        String role = betService.isUserAllowed(betId, login);

        assertEquals(BetService.ROLE_OPPONENT, role);
    }

    @Test
    public void testIsUserAllowed_ExistingBet_Judge() {
        String betId = "123";
        String login = "judgeLogin";
        String judgeId = "judgeId";

        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setId(judgeId);

        BetDto betDto = new BetDto();
        betDto.setId(betId);
        betDto.setJudge(judgeId);

        Bet bet = new Bet();
        bet.setId(betId);
        bet.setJudge(judgeId);

        when(betRepository.findById(betId)).thenReturn(Optional.of(bet));
        when(betService.getBetById(betId)).thenReturn(betDto);
        when(userService.findByLogin(login)).thenReturn(userDto);

        String role = betService.isUserAllowed(betId, login);

        assertEquals(BetService.ROLE_JUDGE, role);
    }

    @Test
    public void testCreateBet_ValidBet() {
        String creatorId = "123";
        BetDto betDto = new BetDto(); // Create a new BetDto object

        // Mock behavior for betMapper.betDtoToBet and betMapper.toBetDto
        Bet bet = new Bet(); // Create an empty Bet object
        when(betMapper.betDtoToBet(betDto)).thenReturn(bet);
        when(betMapper.toBetDto(bet)).thenReturn(betDto); // Return the original BetDto

        // Mock behavior for betRepository.save
        when(betRepository.save(bet)).thenReturn(bet); // Return the saved Bet object

        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        BetDto createdBetDto = betService.createBet(betDto, creatorId);

        // Assertions
        assertNotNull(createdBetDto); // Ensure the created BetDto is not null
        assertEquals(creatorId, createdBetDto.getCreator()); // Verify creator is set correctly
        assertEquals(betDto, createdBetDto); // Verify the returned BetDto is the same object passed in (assuming basic object comparison)
    }

    @Test
    public void testGetBetsByCreatorId_ExistingUser_ActiveBets() {
        String creatorId = "123";
        List<Bet> betsByCreator = new ArrayList<>(); // Create an empty list of Bets
        List<BetDto> expectedActiveBets = new ArrayList<>(); // Empty list for expected active Bets

        // Mock behavior for userRepository.findById
        User user = new User(); // Create a User object
        user.setId(creatorId);
        when(userRepository.findById(creatorId)).thenReturn(Optional.of(user));

        // Mock behavior for betRepository.findBetsByCreator (assuming it returns all bets)
        when(betRepository.findBetsByCreator(creatorId)).thenReturn(Optional.of(betsByCreator));

        // Mock behavior for getActiveBets (assuming it filters based on some logic)
        when(betService.getActiveBets(betsByCreator)).thenReturn(expectedActiveBets); // Return empty list of active bets

        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        List<BetDto> actualActiveBets = betService.getBetsByCreatorId(creatorId);

        // Assertions
        assertNotNull(actualActiveBets); // Ensure the list is not null
        assertTrue(actualActiveBets.isEmpty()); // Verify the returned list is empty (matches expectedActiveBets)
    }

    @Test(expected = AppException.class)
    public void testGetBetsByCreatorId_NonexistentUser() {
        String creatorId = "456";

        // Mock behavior for userRepository.findById to simulate a non-existent user
        when(userRepository.findById(creatorId)).thenReturn(Optional.empty());

        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.getBetsByCreatorId(creatorId); // This should throw an AppException
    }

    @Test(expected = AppException.class)
    public void testGetResolvedBets_NonexistentUser() {
        String userLogin = "nonexistentUser";

        // Mock behavior for userRepository.findByLogin to simulate a non-existent user
        when(userRepository.findByLogin(userLogin)).thenReturn(Optional.empty());

        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.getResolvedBets(userLogin); // This should throw an AppException
    }

    @Test(expected = AppException.class)
    public void testAddOpponent_NonexistentBet() {
        String betId = "nonexistentBet";
        String opponentLogin = "opponentLogin";

        // Mock behavior for betRepository.findBetById to simulate a non-existent bet
        when(betRepository.findBetById(betId)).thenReturn(Optional.empty());

        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.addOpponent(betId, opponentLogin); // This should throw an AppException
    }

    @Test(expected = AppException.class)
    public void testAddOpponent_NonexistentOpponent() {
        String betId = "123";
        String opponentLogin = "nonexistentOpponent";

        // Mock behavior for betRepository.findBetById
        Bet bet = new Bet(); // Create an empty Bet object
        when(betRepository.findBetById(betId)).thenReturn(Optional.of(bet));

        UserService userService = new UserService(userRepository, null, userMapper);
        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.addOpponent(betId, opponentLogin); // This should throw an AppException
    }

    @Test(expected = AppException.class)
    public void testAddOpponent_OpponentIsJudge() {
        String betId = "123";
        String opponentLogin = "opponentLogin";
        String judgeId = "456";

        // Mock behavior for betRepository.findBetById
        Bet bet = new Bet();
        bet.setJudge(judgeId); // Set judge for the bet
        when(betRepository.findBetById(betId)).thenReturn(Optional.of(bet));

        // Mock behavior for userService.findByLogin
        // Opponent same as judge
        User user = new User();
        user.setId(judgeId);
        user.setLogin(opponentLogin);
        UserDto userDto = userMapper.toUserDto(user);

        UserService userService = new UserService(userRepository, null, userMapper);
        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.addOpponent(betId, opponentLogin); // This should throw an AppException
    }

    @Test(expected = AppException.class)
    public void testAddOpponent_OpponentIsCreator() {
        String betId = "123";
        String creatorLogin = "creatorLogin";

        // Mock behavior for betRepository.findBetById
        Bet bet = new Bet();
        bet.setCreator(creatorLogin); // Set creator for the bet
        when(betRepository.findBetById(betId)).thenReturn(Optional.of(bet));

        // Mock behavior for userService.findByLogin
        User user = new User();
        user.setLogin(creatorLogin); // Opponent same as creator
        UserDto userDto = new UserDto();
        userDto = userMapper.toUserDto(user);

        UserService userService = new UserService(userRepository, null, userMapper);
        BetService betService = new BetService(betRepository, userRepository, userService, betMapper); // Initialize BetService with mocks

        betService.addOpponent(betId, creatorLogin); // This should throw an AppException
    }

}