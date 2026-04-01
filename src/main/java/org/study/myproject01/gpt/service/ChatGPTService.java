package org.study.myproject01.gpt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatGPTService {
    @Value("${}")
    private String RAWAPIKEY;
    @Value("${}")
    private String RAWMODEL;

    private static final String API_URL = "";
}
