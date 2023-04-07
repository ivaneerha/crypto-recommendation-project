package com.xm.recommendation.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class ErrorMessage {

    private String message;

    private int status;

    private LocalDateTime time;

}