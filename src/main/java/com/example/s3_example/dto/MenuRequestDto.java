package com.example.s3_example.dto;

import lombok.Getter;

import java.net.URL;

@Getter
public class MenuRequestDto {
    private Long restaurantId;
    private URL image;
    private String name;
    private int cost;

    public void setImage(URL updatedFileName) {
        this.image = updatedFileName;
    }
}
