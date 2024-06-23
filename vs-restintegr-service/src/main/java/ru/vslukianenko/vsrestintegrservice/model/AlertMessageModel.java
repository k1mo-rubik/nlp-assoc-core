package ru.vslukianenko.vsrestintegrservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AlertMessageModel{
    private String message;
    private long   time;
    private String appName;
}
