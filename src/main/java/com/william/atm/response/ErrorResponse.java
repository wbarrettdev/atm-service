package com.william.atm.response;

import lombok.Data;

@Data
public class ErrorResponse extends BaseResponse{
    private String errorMessage;
}
