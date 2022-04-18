package com.william.atm.service;

import com.william.atm.domain.Account;
import com.william.atm.domain.Note;
import com.william.atm.exception.ATMInsufficientException;
import com.william.atm.exception.InsufficientFundsException;
import com.william.atm.response.WithdrawResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    NoteServiceImpl noteService = Mockito.mock(NoteServiceImpl.class);
    AccountServiceImpl accountService = Mockito.mock(AccountServiceImpl.class);
    TransactionServiceImpl transactionService = new TransactionServiceImpl(noteService, accountService);

    Account account;

    @BeforeEach
    void beforeEach(){
        account = new Account(123456789L,(short) 1234, new BigDecimal(800), new BigDecimal(200));
        when(accountService.getAccount(123456789L)).thenReturn(account);
    }

    @Test
    void withdrawHappyPathTest() {
        List<Note> notesList = new ArrayList<>();
        notesList.add(new Note(50, 10));

        when(noteService.getNotes()).thenReturn(notesList);
        WithdrawResponse response = transactionService.withdraw(123456789L, 100);

        //Test the correct notes were dispensed and balance deducted
        List<Note> responseNotes = response.getDispensedNotes();
        assertEquals(response.getBalance(), BigDecimal.valueOf(700));
        assertTrue(responseNotes.size() ==1);
        assertTrue( responseNotes.get(0).getNumberOfNotes() == 2);
        verify(noteService, times(1)).deleteAllEntriesWithZeroNotes();
    }

    @Test
    void atmHasIncorrectNoteCompositionTest(){
        List<Note> notesList = new ArrayList<>();
        notesList.add(new Note(50, 1));
        notesList.add(new Note(20, 1));
        notesList.add(new Note(10, 1));

        when(noteService.getNotes()).thenReturn(notesList);

        assertThrows(ATMInsufficientException.class,
                () -> transactionService.withdraw(123456789L, 15));

    }

    @Test
    void accountHasInsufficientFundsTest(){
        List<Note> notesList = new ArrayList<>();
        notesList.add(new Note(50, 200));

        when(noteService.getNotes()).thenReturn(notesList);

        assertThrows(InsufficientFundsException.class,
                () -> transactionService.withdraw(123456789L, 1200));
    }
}