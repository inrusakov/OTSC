package com.otsc.backend.mappers;

import com.otsc.backend.dtos.BetDto;
import com.otsc.backend.dtos.ContestDto;
import com.otsc.backend.entities.Bet;
import com.otsc.backend.entities.Contest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContestMapper {
    ContestDto toContestDto(Contest contest);

    List<ContestDto> toContestDtos(List<Contest> contests);

    Contest contestDtoToContest(ContestDto betDto);
}
