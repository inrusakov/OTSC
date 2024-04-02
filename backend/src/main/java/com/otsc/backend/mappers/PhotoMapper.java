package com.otsc.backend.mappers;

import com.otsc.backend.dtos.PhotoDto;
import com.otsc.backend.entities.Photo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    Photo photoDtoToPhoto (PhotoDto photo);
    PhotoDto photoToPhotoDto (Photo photoDto);
}
