package lt.javau12.Sales.dtos;

public record OrderItemResponseDTO(
        String goodsNames,
        Double price,
        Integer quantity
) {
}
