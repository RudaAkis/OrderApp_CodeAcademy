package lt.javau12.Sales.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        LocalDateTime createdAt,
        String customerName,
        List<OrderItemResponseDTO> items
) {

}
