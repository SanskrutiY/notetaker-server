package com.example.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Note;
import com.example.service.NoteService;
import java.util.*;

@CrossOrigin(
	    origins = "http://localhost:3000",
	    allowCredentials = "true"
	)
@RestController
@RequestMapping("/api/froggy/note")
public class NoteController {
	
	@Autowired
	NoteService noteService;
	
	@PostMapping
	public ResponseEntity<Note> createNote(@RequestBody Note note){
		Note createdNote = noteService.createNote(note.getMood(), 
				note.getNoteTitle(), note.getNoteContent(), note.getImageUrl());
		return ResponseEntity.ok(createdNote);
	}
	
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(id, note);
        return ResponseEntity.ok(updatedNote);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok("Note deleted successfully!");
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotesAndDeletedFalse() {
        return ResponseEntity.ok(noteService.getAllNotesAndDeletedFalse());
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<Note>> getNotesAndDeletedTrue() {
        return ResponseEntity.ok(noteService.getNotesAndDeletedTrue());
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNotebyId(id));
    }
    
    @GetMapping("/search/date")
    public ResponseEntity<List<Note>> getByParticularDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(noteService.getByParticularDate(date));
    }
    
    @GetMapping("/search/from-date")
    public ResponseEntity<List<Note>> getFromParticularDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(noteService.getFromParticularDate(date));
    }
    
}
