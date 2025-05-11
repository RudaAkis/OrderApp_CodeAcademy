package lt.javau12.Sales.services;

import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.repositories.GoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ImageService {

    private final GoodsRepository goodsRepository;

    public ImageService(GoodsRepository goodsRepository){
        this.goodsRepository = goodsRepository;
    }


    public void
    uploadGoodsImage(Long goodsId, MultipartFile imageFile) throws IOException {
        // Fetch and update the goods
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("Goods not found"));

        // Read the uploaded image as BufferedImage
        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

        // Convert and save as JPEG regardless of input format
        String fileName = "goods-" + goodsId + ".jpg";
        Path uploadPath = Paths.get("src/main/resources/static/uploads", fileName);

        try (OutputStream os = Files.newOutputStream(uploadPath)) {
            ImageIO.write(bufferedImage, "jpg", os);
        }

        // Save the relative path to the database
        goods.setImagePath("uploads/" + fileName);
        goodsRepository.save(goods);

    }

    public String getImagePathForGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("Goods not found"));
        return goods.getImagePath();
    }
}
