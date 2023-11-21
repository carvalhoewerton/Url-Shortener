package com.msurlshortener.repository;

import com.msurlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.msurlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortyRepository extends JpaRepository<Url, Long> {
    Url findByHashedUrl(String hashedUrl);
}