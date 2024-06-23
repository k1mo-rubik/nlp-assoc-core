package ru.vslukianenko.vscoreservice.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class ServiceParams {

    private String appName = "vs-core-service";

    private String alertSrvUrl = "http://localhost:8082/report";

}