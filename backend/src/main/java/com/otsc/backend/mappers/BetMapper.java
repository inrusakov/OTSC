package com.otsc.backend.mappers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.entities.Bet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BetMapper {

    BetDto toBetDto(Bet bet);

    Bet betDtoToBet(BetDto betDto);
}
