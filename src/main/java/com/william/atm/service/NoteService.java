package com.william.atm.service;

import com.william.atm.domain.Note;

import java.util.List;

public interface NoteService {
    /**
     * Gets a list of banknotes currently in the ATM
     *
     * @return Ordered list notes with the Largest first.
     */
    List<Note> getNotes();

    /**
     * Saves an updated list of banknotes
     */
    void saveAll(List<Note> notes);

    /**
     * delete all banknote entries from database that have no notes left in machine.
     */
    void deleteAllEntriesWithZeroNotes();
}
