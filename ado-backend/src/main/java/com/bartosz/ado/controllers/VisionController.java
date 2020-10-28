/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bartosz.ado.controllers;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins =  "http://localhost:4200")
@RestController
public class VisionController {

  @Autowired private ResourceLoader resourceLoader;


  @Autowired private CloudVisionTemplate cloudVisionTemplate;



  @PostMapping("/extractLabels")
  public Map<String, Float> extractLabels(@RequestParam("File") MultipartFile file, ModelMap map) {

    AnnotateImageResponse response =
        this.cloudVisionTemplate.analyzeImage(
            file.getResource(), Type.LABEL_DETECTION);

    Map<String, Float> imageLabels =
        response
            .getLabelAnnotationsList()
            .stream()
            .collect(
                Collectors.toMap(
                    EntityAnnotation::getDescription,
                    EntityAnnotation::getScore,
                    (u, v) -> {
                      throw new IllegalStateException(String.format("Duplicate key %s", u));
                    },
                    LinkedHashMap::new));


    map.addAttribute("annotations", imageLabels);
    map.addAttribute("imageUrl", file.getName());

      return imageLabels;
  }

  @GetMapping("/extractText")
  public String extractText(String imageUrl) {
    String textFromImage =
        this.cloudVisionTemplate.extractTextFromImage(this.resourceLoader.getResource(imageUrl));
    return "Text from image: " + textFromImage;
  }
}