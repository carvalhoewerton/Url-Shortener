package com.msurlshortener.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="tb_url")
@NoArgsConstructor
@Getter
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
    private String hashedUrl;

    public Url(String url) {
        this.url = url;
    }
}
