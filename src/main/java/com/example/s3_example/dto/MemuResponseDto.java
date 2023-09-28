package com.example.s3_example.dto;

import com.example.s3_example.model.Menu;
import lombok.Getter;

import java.net.URL;

@Getter
public class MemuResponseDto {
    public Long id;
    private Long restaurantId;
    private URL image;
    private String name;
    private int cost;

    public MemuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.restaurantId = menu.getRestaurant().getId();
        this.image = menu.getImage();
        this.name = menu.getName();
        this.cost = menu.getCost();
    }
}
