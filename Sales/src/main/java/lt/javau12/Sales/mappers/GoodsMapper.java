package lt.javau12.Sales.mappers;

import lt.javau12.Sales.dtos.GoodsDTO;
import lt.javau12.Sales.models.Goods;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper {

    public GoodsDTO toDTO(Goods entity){
        return new GoodsDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImagePath()
        );
    }

    public Goods toEntity(GoodsDTO dto){
        return new Goods(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getImagePath()
        );
    }
    public Goods toEntity(Long id,GoodsDTO dto){
        return new Goods(
                id,
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getImagePath()
        );
    }
}
