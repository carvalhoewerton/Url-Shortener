package com.msurlshortener.service;

import com.msurlshortener.model.Url;
import com.msurlshortener.repository.ShortyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ShortyService {

    private final MessageDigest digest;

    @Autowired
    private ShortyRepository shortyRepository;

    public ShortyService() {
        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao inicializar o MessageDigest.", e);
        }
    }


    public String hash(String url, int length) {
        byte[] bytes = digest.digest(url.getBytes());
        String hash = String.format("%032x", new BigInteger(1, bytes));

        return hash.substring(0, length);
    }

    public Url save(Url url) {
        return shortyRepository.save(url);
    }

    public Url resolve(String hashedUrl) {
        return shortyRepository.findByHashedUrl(hashedUrl);
    }
}
