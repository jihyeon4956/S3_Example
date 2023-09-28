package com.example.s3_example.model;


import com.example.s3_example.dto.MenuRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private URL image;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int cost;

    public Menu(MenuRequestDto requestDto, Restaurant restaurant, URL storedFileName) {
        this.id = getId();
        this.restaurant = restaurant;
        this.image = storedFileName;
        this.name = requestDto.getName();
        this.cost = requestDto.getCost();
    }

    public Menu(String name, int cost, Restaurant restaurant, URL imageUrl) {
        this.id = getId();
        this.restaurant = restaurant;
        this.image = imageUrl;
        this.name = name;
        this.cost = cost;
    }


    public void update(MenuRequestDto requestDto) {
        this.image = requestDto.getImage();
        this.name = requestDto.getName();
        this.cost = requestDto.getCost();
    }

    public void update(URL updatedImageUrlObject, MenuRequestDto requestDto) {
        this.image = updatedImageUrlObject;
        this.name = requestDto.getName();
        this.cost = requestDto.getCost();
    }

    public void update(URL updatedImageUrlObject, String name, int cost) {
        this.image = updatedImageUrlObject;
        this.name = name;
        this.cost = cost;
    }
}
