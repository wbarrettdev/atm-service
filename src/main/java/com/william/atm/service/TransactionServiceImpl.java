package com.william.atm.service;

import com.william.atm.domain.Account;
import com.william.atm.domain.Note;
import com.william.atm.exception.ATMInsufficientException;
import com.william.atm.exception.InsufficientFundsException;
import com.william.atm.response.WithdrawResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    final NoteService notesService;
    final AccountService accountService;

    public TransactionServiceImpl(NoteService notesService, AccountService accountService) {
        this.notesService = notesService;
        this.accountService = accountService;
    }

    /**
     * @inheritDoc
     */
    @Transactional
    synchronized public WithdrawResponse withdraw(long accountId, long amount){
        WithdrawResponse withdrawResponse = new WithdrawResponse();
        List<Note> notes = notesService.getNotes();
        Account account = accountService.getAccount(accountId);

        // ATM does not have enough notes to dispense.
        if(notes.stream().mapToLong(e -> (long) e.getId() *e.getNumberOfNotes()).sum() < amount)
            throw new ATMInsufficientException("ATM has insufficient funds.");

        // Account has insufficient funds.
        if (account.getMaxWithdrawal().compareTo(BigDecimal.valueOf(amount)) < 0 )
            throw new InsufficientFundsException("Account has insufficient funds.");


        // Last member of notes List is guaranteed to be the smallest note.
        if(amount % notes.get(notes.size()-1).getId() != 0)
            throw new ATMInsufficientException("This ATM can only dispense multiples of "
                    + notes.get(notes.size()-1).getId());

        List<Note> notesToDispense = new ArrayList<>();
        int runningTotal = 0;

        //Iterate the notes and determine what to dispense
        for(int note = 0; note < notes.size(); note++) {
            Note currentNote = notes.get(note);
            int noteTotal = 0;

            for (int noteCounter = notes.get(note).getNumberOfNotes(); noteCounter > 0; noteCounter--) {
                if (runningTotal + currentNote.getId() <= amount) {
                    noteTotal += 1;
                    runningTotal += currentNote.getId();
                    currentNote.setNumberOfNotes(currentNote.getNumberOfNotes() - 1);
                } else break;
            }

            if (noteTotal > 0){
                notesToDispense.add(new Note(currentNote.getId(), noteTotal));
                notes.set(note, currentNote);
            }

            if( runningTotal == amount) break;
        }

        // ATM does not have the note combination to dispense selected amount;
        if(amount != runningTotal)
            throw new ATMInsufficientException("Insufficient note combination to dispense amount");

        notesService.saveAll(notes);
        notesService.deleteAllEntriesWithZeroNotes();
        account.subtractAmount(amount);
        accountService.save(account);

        withdrawResponse.setDispensedNotes(notesToDispense);
        withdrawResponse.setBalance(account.getBalance());
        withdrawResponse.setAccountId(String.valueOf(accountId));

        return withdrawResponse;
    }
}
