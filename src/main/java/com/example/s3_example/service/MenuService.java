package com.example.s3_example.service;


import com.example.s3_example.dto.MemuResponseDto;
import com.example.s3_example.dto.MessageResponseDto;
import com.example.s3_example.model.Menu;
import com.example.s3_example.model.Restaurant;
import com.example.s3_example.repository.MenuRepository;
import com.example.s3_example.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final S3Uploader s3Uploader;

    public MemuResponseDto createMenu(Long id, MultipartFile image, String name, int cost) throws IOException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("음식점을 찾을 수 없습니다"));
        if(image.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }
        String storedFileName = s3Uploader.upload(image,"images");
        URL imageUrl = new URL(storedFileName);

        Menu menu = menuRepository.save(new Menu(name, cost, restaurant, imageUrl));
        return new MemuResponseDto(menu);
    }

    public List<MemuResponseDto> getMenus() {
        List<Menu> menuList = menuRepository.findAll();
        return menuList.stream().map(MemuResponseDto::new).toList();
    }

    public MemuResponseDto selectMenu(Long id) {
        Menu menu = findMenu(id);
        return new MemuResponseDto(menu);
    }

    @Transactional
    public MemuResponseDto update(Long id, MultipartFile newImage, String name, int cost) throws IOException {
        Menu menu = findMenu(id);
        Restaurant restaurant = menu.getRestaurant();

        String oldFileUrl = menu.getImage().getPath().substring(1);
        String updatedImageUrl = s3Uploader.updateFile(newImage, oldFileUrl, "images");
        URL updatedImageUrlObject = new URL(updatedImageUrl);
        menu.update(updatedImageUrlObject, name, cost);
        return new MemuResponseDto(menu);
    }


    public ResponseEntity<MessageResponseDto> deleteMenu(Long id) {
        Menu menu = findMenu(id);
        String filePathInS3 = menu.getImage().getPath().substring(1);
        s3Uploader.deleteFile(filePathInS3); // S3에서 이미지 파일 삭제
        menuRepository.delete(menu); // DB에서 메뉴 삭제
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponseDto("메뉴 삭제 성공!"));
    }


    // 선택한 메뉴가 존재하는지 검사하는 메서드
    private Menu findMenu(Long id) {
        return menuRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메뉴는 존재하지 않습니다."));
    }
}
