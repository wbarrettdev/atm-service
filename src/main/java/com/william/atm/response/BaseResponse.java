package com.william.atm.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
abstract class BaseResponse {
    @Setter(AccessLevel.NONE)
    private LocalDateTime timestamp = LocalDateTime.now();
    private boolean hasErrors = false;
    private String errorMessage;
    private int status;
}
