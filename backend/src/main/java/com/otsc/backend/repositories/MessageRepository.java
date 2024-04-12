package com.otsc.backend.repositories;

import com.otsc.backend.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findAllByBetIdOrderByIdAsc(String id);
}
