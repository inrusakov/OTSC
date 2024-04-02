package com.otsc.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Photo {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String title;

    private Binary image;
}
