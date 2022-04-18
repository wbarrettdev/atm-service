package com.william.atm.service;

import com.william.atm.domain.Note;
import com.william.atm.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NotesRepository notesRepository;

    /**
     * @inheritDoc
     */
    public List<Note> getNotes(){
        List<Note> notes = (List<Note>) notesRepository.findAll();
        Collections.sort(notes, Collections.reverseOrder());
        return notes;
    }

    /**
     * @inheritDoc
     */
    public void saveAll(List<Note> notes){
        notesRepository.saveAll(notes);
    }

    /**
     * @inheritDoc
     */
    public void deleteAllEntriesWithZeroNotes(){
        List<Note> notes = getNotes();

        for(Note note: notes){
            if (note.getNumberOfNotes() == 0)
                notesRepository.deleteById(note.getId());
        }
    }
}
