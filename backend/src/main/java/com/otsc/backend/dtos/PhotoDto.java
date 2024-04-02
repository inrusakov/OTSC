package com.otsc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDto {
    private String id;

    private String title;

    private Binary image;
}
