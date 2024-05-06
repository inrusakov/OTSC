package com.otsc.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private String id;
    private String betId;
    private String alignment;
    private String avatar;
    private String name;
    private String text;
    private boolean imageInMessage;
    private String imagePath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

}
