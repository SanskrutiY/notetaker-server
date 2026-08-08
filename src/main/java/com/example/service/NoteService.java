package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Note;
import com.example.repository.NoteRepository;

@Service
public class NoteService {
	
	@Autowired
	NoteRepository noteRepository;
	
	public Note createNote(String mood, String title, String content, String imageUrl) {
		Note newNote = new Note();
		newNote.setMood(mood);
		newNote.setNoteTitle(title != null ? title.trim() : "Untitled");
		newNote.setNoteContent(content);
		newNote.setImageUrl(imageUrl);
		newNote.setDeleted(false);
		newNote.setCreatedOn(LocalDateTime.now());
		newNote.setUpdatedOn(LocalDateTime.now());
		return noteRepository.save(newNote);
	}
	
	public Note updateNote(Long id, Note note){
		Note existingNote = noteRepository.findById(id).orElseThrow(()-> new RuntimeException("Id "+id+" not found"));
		if(note.getMood() != null)
			existingNote.setMood(note.getMood());
		if(note.getNoteTitle() != null)
			existingNote.setNoteTitle(note.getNoteTitle().trim());
		if(note.getNoteContent() != null)
			existingNote.setNoteContent(note.getNoteContent());
		if(note.getImageUrl() != null)
			existingNote.setImageUrl(note.getImageUrl());
		existingNote.setUpdatedOn(LocalDateTime.now());
		return noteRepository.save(existingNote);
	}
	
	public void deleteNote(Long id) {
		Note existingNote = noteRepository.findById(id).orElseThrow(()-> new RuntimeException("Id "+id+" not found"));
		existingNote.setDeleted(true);
		noteRepository.save(existingNote);
	}
	
	public List<Note> getAllNotes(){
		return noteRepository.findAll();
	}
	
	public List<Note> getAllNotesAndDeletedFalse(){
		List<Note> allNotes = noteRepository.findAll();
//		List<Note> notDeletedNotes = allNotes.stream().filter(note -> !note.isDeleted()).collect(Collectors.toList());
		List<Note> notDeletedNotes = allNotes.stream()
							        .filter(note -> !note.getIsDeleted())
							        .sorted(Comparator.comparing(Note::getUpdatedOn).reversed())
							        .collect(Collectors.toList());
		return notDeletedNotes;
	}
	
	public List<Note> getNotesAndDeletedTrue(){
		List<Note> allNotes = noteRepository.findAll();
		List<Note> deletedNotes = allNotes.stream().filter(note -> note.getIsDeleted()).collect(Collectors.toList());
		return deletedNotes;
	}

	public Note restoreNoteById(Long id) {
		Note note = noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Id " + id + " not found"));
		note.setDeleted(false);
		noteRepository.save(note);
		return note;
	}
	
	public Note getNotebyId(Long id) {
		return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Id " + id + " not found"));
	}
	
	public List<Note> getByParticularDate(LocalDate date) {
	    LocalDateTime start = date.atStartOfDay();
	    LocalDateTime end = date.plusDays(1).atStartOfDay();
	    List<Note> filteredNotes = noteRepository.findByCreatedOnBetween(start, end);
	    return filteredNotes.stream().filter(n -> !n.getIsDeleted()).collect(Collectors.toList());
	}
	
	public List<Note> getFromParticularDate(LocalDate date) {
	    LocalDateTime start = date.atStartOfDay();
	    List<Note> filteredNotes = noteRepository.findByCreatedOnAfter(start);
	    return filteredNotes.stream().filter(n -> !n.getIsDeleted()).collect(Collectors.toList());
	}
	
}
