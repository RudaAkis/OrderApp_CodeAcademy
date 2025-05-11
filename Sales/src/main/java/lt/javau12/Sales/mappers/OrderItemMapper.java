package lt.javau12.Sales.mappers;

import lt.javau12.Sales.dtos.OrderItemDTO;
import lt.javau12.Sales.dtos.OrderItemResponseDTO;
import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.models.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemDTO toDTO(OrderItem entity){
        return new OrderItemDTO(
                entity.getGoods().getId(),
                entity.getQuantity(),
                entity.getPriceAtOrderTime()
        );
    }

    public OrderItemResponseDTO toOrderItemResponseDTO(OrderItem entity){
        return new OrderItemResponseDTO(
          entity.getGoods().getName(),
          entity.getPriceAtOrderTime(),
          entity.getQuantity()
        );
    }

    public OrderItem toEntity(OrderItemDTO dto, Goods goods){
        return new OrderItem(
                dto.getQuantity(),
                goods.getPrice(),
                goods
        );
    }
}
