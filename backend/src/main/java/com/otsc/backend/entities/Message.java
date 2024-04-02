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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bet_id",nullable = false)
    private Long betId;

    @Column(name = "alignment",nullable = false)
    private String alignment;

    @Column(name = "avatar",nullable = false)
    private String avatar;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "text",nullable = false)
    private String text;

    @Column(name = "image_in_message",nullable = false)
    private boolean imageInMessage;

    @Column(name = "image_path", nullable = true)
    private String imagePath;

    @Column(name = "time",nullable = false)
    private String time;
}
