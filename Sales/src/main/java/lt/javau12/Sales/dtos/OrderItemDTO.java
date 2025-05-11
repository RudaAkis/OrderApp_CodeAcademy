package lt.javau12.Sales.dtos;

import jakarta.validation.constraints.Min;
import lt.javau12.Sales.models.Goods;

public class OrderItemDTO {

    private Long goodsId;
    @Min(1)
    private Integer quantity;
    private Double price;

    public OrderItemDTO(Long goodsId, Integer quantity, Double price) {
        this.goodsId = goodsId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemDTO() {
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
