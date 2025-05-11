package lt.javau12.Sales.controllers;

import lt.javau12.Sales.dtos.GoodsDTO;
import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.services.GoodsService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

    private final GoodsService goodsService;

    public GoodsController(GoodsService goodsService){
        this.goodsService = goodsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GoodsDTO>> getAllGoods(){
        return ResponseEntity.ok(goodsService.getAllGoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoodsById(@PathVariable Long id){
        return ResponseEntity.ok(goodsService.getGoodsbyId(id));
    }

    @PostMapping
    public ResponseEntity<GoodsDTO> create(@RequestBody GoodsDTO goodsDTO){
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(goodsService.create(goodsDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoodsDTO> update(@PathVariable Long id, @RequestBody GoodsDTO goodsDTO){
        return ResponseEntity.of(goodsService.update(id, goodsDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id){
        return goodsService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


}
