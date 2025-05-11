package lt.javau12.Sales.controllers;

import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.repositories.GoodsRepository;
import lt.javau12.Sales.services.ImageService;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    private final ImageService imageService;


    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @GetMapping("/{goodsId}")
    public ResponseEntity<byte []> getImageByGoodsId(@PathVariable Long goodsId) throws IOException {
        return ResponseEntity.ok(imageService.getImageFromFileByGoodsId(goodsId));
    }

    @PostMapping("/{goodsId}")
    public ResponseEntity<Void> uploadImageByGoodsId(@PathVariable Long goodsId, @RequestParam("imageFile") MultipartFile file) throws IOException {
        imageService.uploadAnImage(goodsId, file);
        return ResponseEntity.ok().build();
    }


}