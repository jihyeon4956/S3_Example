package com.example.s3_example.controller;


import com.example.s3_example.dto.MemuResponseDto;
import com.example.s3_example.dto.MenuRequestDto;
import com.example.s3_example.dto.MessageResponseDto;
import com.example.s3_example.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menus")
    public MemuResponseDto createMenu(
            @RequestParam("restaurantId") Long restaurantId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("cost") int cost) throws IOException {
        return menuService.createMenu(restaurantId, image, name, cost);
    }

    @GetMapping("/menus")
    public List<MemuResponseDto> getMenus(){
        return menuService.getMenus();
    }

    @GetMapping("/menus/{id}")
    public MemuResponseDto selectMenu(@PathVariable Long id){
        return menuService.selectMenu(id);
    }

    @PutMapping("/menus/{id}")
    public MemuResponseDto updateMenu(@PathVariable Long id,
                                      @RequestParam(value = "image") MultipartFile newImage,
                                      @RequestParam("name") String name,
                                      @RequestParam("cost") int cost) throws IOException {
        return menuService.update(id, newImage, name, cost);
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<MessageResponseDto> deleteMenu(@PathVariable Long id){
        return menuService.deleteMenu(id);
    }
}
