package lt.javau12.Sales.services;


import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.repositories.GoodsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    private final GoodsRepository goodsRepository;

    private final String IMAGE_BASE_DESTINATION = "C:/Users/gvida/OneDrive/Desktop/SalesProject/Sales";
    private final String IMAGE_FOLDER = "/uploads/";

    public ImageService(GoodsRepository goodsRepository){
        this.goodsRepository = goodsRepository;
    }

    public byte [] getImageFromFileByGoodsId(Long goodsId) throws IOException {
        Goods goods = getGoodsById(goodsId);

        String imageFileName = goods.getImagePath();
        String fullPathToImage = IMAGE_BASE_DESTINATION + IMAGE_FOLDER + imageFileName;
        File imageFile = new File(fullPathToImage);
        BufferedImage image = ImageIO.read(imageFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }

    public void uploadAnImage(Long goodsId, MultipartFile imageFile) throws IOException {
        Goods goods = getGoodsById(goodsId);

        String imageFileName = "goods-"+goodsId+".jpg";
        String fullPath = IMAGE_BASE_DESTINATION + IMAGE_FOLDER + imageFileName;
        File file = new File(fullPath);
        BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
        ImageIO.write(bufferedImage, "jpg", file);
        goods.setImagePath(imageFileName);
        goodsRepository.save(goods);
    }

    public Goods getGoodsById(Long goodsId){
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("Goods was not found by Id" + goodsId));
    }

}