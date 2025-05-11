package lt.javau12.Sales.mappers;

import lt.javau12.Sales.dtos.OrderDTO;
import lt.javau12.Sales.dtos.OrderItemResponseDTO;
import lt.javau12.Sales.models.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order entity, List<OrderItemResponseDTO> orderItemResponseDTOS){
        return new OrderDTO(
          entity.getId(),
          entity.getCreatedAt(),
          entity.getCustomer().getName(),
                orderItemResponseDTOS
        );
    }
}
