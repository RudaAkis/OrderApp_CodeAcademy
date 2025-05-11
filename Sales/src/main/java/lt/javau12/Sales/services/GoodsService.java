package lt.javau12.Sales.services;

import com.sun.source.tree.OpensTree;
import lt.javau12.Sales.dtos.GoodsDTO;
import lt.javau12.Sales.mappers.GoodsMapper;
import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.repositories.GoodsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;
    private final GoodsMapper goodsMapper;

    public GoodsService(GoodsRepository goodsRepository,
                        GoodsMapper goodsMapper){
        this.goodsRepository = goodsRepository;
        this.goodsMapper = goodsMapper;
    }

    public List<GoodsDTO> getAllGoods(){
        return goodsRepository.findAll()
                .stream()
                .map(goodsMapper::toDTO)
                .toList();
    }

    public Goods getGoodsbyId(Long id){
        return goodsRepository.findById(id).orElseThrow( () -> new RuntimeException("Goods not found"));
    }

    public GoodsDTO create(GoodsDTO goodsDTO){
        //Saves the goods to the repository by transforming DTO to entity and
        // the returned entity is transformed back to DTO to pass to the front end
        Goods entity = goodsMapper.toEntity(goodsDTO);
        return goodsMapper.toDTO( goodsRepository.save(entity ) );
    }

    public Optional<GoodsDTO> update(Long id, GoodsDTO goodsDTO){
        if (goodsRepository.existsById(id)){
            Goods goodsToUpdate = getGoodsbyId(id);
            goodsToUpdate.setName(goodsDTO.getName());
            goodsToUpdate.setDescription(goodsDTO.getDescription());
            goodsToUpdate.setPrice(goodsDTO.getPrice());
            System.out.println(goodsToUpdate.getId());
            return Optional.of( goodsMapper.toDTO( goodsRepository.save(goodsToUpdate )));
        }
        return Optional.empty();
    }

    public boolean delete(Long id){
        if (goodsRepository.existsById(id)){
            goodsRepository.delete(getGoodsbyId(id));
            return true;
        }
        return false;
    }
}
