package lt.javau12.Sales.controllers;


import lt.javau12.Sales.services.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @GetMapping("/{goodsId}")
    public ResponseEntity<String> getImageByGoodsId(@PathVariable Long goodsId){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(imageService.getImagePathForGoods(goodsId) );
    }

    @PostMapping("/{goodsId}")
    public ResponseEntity<Void> uploadImageByGoodsId(@PathVariable Long goodsId, @RequestParam MultipartFile imageFile) throws IOException {
        imageService.uploadGoodsImage(goodsId, imageFile);
        return ResponseEntity.ok().build();
    }


}
