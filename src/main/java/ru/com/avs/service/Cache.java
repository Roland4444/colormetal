package ru.com.avs.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("Cache")
public class Cache {
    public ResponseEntity cachedSettings;
    public boolean cacheready = false;

}
