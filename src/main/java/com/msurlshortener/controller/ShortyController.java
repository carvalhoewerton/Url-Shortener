package com.msurlshortener.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msurlshortener.model.Url;
import com.msurlshortener.model.UrlDto;
import com.msurlshortener.service.ShortyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class ShortyController {

    @Autowired
    private ShortyService shortyService;

    @PostMapping("/shorten")
    public Url shorten(@RequestBody UrlDto urlDto) {
        Url dburl = new Url();
        BeanUtils.copyProperties(urlDto, dburl);

        dburl.setHashedUrl(shortyService.hash(dburl.getUrl(), 6));

        return shortyService.save(dburl);
    }
    @GetMapping("/{hashedUrl}")
    public ResponseEntity<Void> resolve(@PathVariable String hashedUrl) {
        Url url = shortyService.resolve(hashedUrl);

        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            URI targetUri = new URI(url.getUrl());
            return ResponseEntity
                    .status(301)
                    .location(targetUri)
                    .header("Connection", "close")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}