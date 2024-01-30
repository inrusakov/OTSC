package com.otsc.backend.mappers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.entities.Bet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetMapper {

    BetDto toBetDto(Bet bet);

    List<BetDto> toBetDtos(List<Bet> bets);

    Bet betDtoToBet(BetDto betDto);
}
