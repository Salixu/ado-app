package com.bartosz.ado.models.mappers;

import com.bartosz.ado.models.Image;
import com.bartosz.ado.models.dtos.ImageDto;
import com.bartosz.ado.services.GoogleTranslate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ImageMapper {


    public ImageMapper(){
    }

    public static ImageDto mapToImageDto(Image image){
        GoogleTranslate googleTranslate =  new GoogleTranslate();

        return ImageDto.builder()
                .id(image.getId())
                .imageName(image.getImage_name())
                .description(googleTranslate.StringToMapPlain(image.getDescription()))
                .image(image.getImage())
                .build();
    }

    public static List<ImageDto> mapToImagesDtos(List<Image> images){
        return  images.stream().map(image -> mapToImageDto(image)).collect(Collectors.toList());
    }

}
