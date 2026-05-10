package com.example.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	List<Note> findByCreatedOnBetween(LocalDateTime start, LocalDateTime end);
	List<Note> findByCreatedOnAfter(LocalDateTime start);
}
