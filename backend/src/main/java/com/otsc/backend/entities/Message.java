package com.otsc.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "bet_id", nullable = false)
    private String betId;

    @Column(name = "alignment", nullable = false)
    private String alignment;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "image_in_message", nullable = false)
    private boolean imageInMessage;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

}
