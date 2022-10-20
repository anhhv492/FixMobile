package com.fix.mobile.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SaleException extends RuntimeException {
    private String message;
}
