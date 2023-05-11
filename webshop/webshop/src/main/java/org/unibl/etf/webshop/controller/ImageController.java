package org.unibl.etf.webshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {

    @Autowired
    private Environment environment;

    @GetMapping("/{imageName}")
    public byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
        Path root = Paths.get(environment.getProperty("file.upload.path")).toAbsolutePath();
        Path path = root.resolve(imageName);
        log.info("Success getting image on url localhost:8080/images. Image name: " + imageName);
        return Files.readAllBytes(path);
    }
}
