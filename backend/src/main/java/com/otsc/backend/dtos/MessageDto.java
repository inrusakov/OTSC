package com.otsc.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private Long betId;
    private String alignment;
    private String avatar;
    private String name;
    private String text;
    private boolean imageInMessage;
    private String imagePath;
    private String time;

}
