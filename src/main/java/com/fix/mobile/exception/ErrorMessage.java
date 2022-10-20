package com.fix.mobile.exception;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorMessage {
    private String code;
    private String message;
}
