package com.william.atm.response;

import com.william.atm.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
final public class WithdrawResponse  extends BaseResponse{
    private String accountId;
    private BigDecimal balance;
    private List<Note> dispensedNotes;
}
