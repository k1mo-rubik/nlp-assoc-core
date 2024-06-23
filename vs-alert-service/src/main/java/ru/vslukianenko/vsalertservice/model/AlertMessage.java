package ru.vslukianenko.vsalertservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AlertMessage {
    private String message;
    private long   time;
    private String appName;
}
