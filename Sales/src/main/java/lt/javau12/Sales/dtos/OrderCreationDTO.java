package lt.javau12.Sales.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderCreationDTO {

    @NotNull
    private Long customerId;

    @Valid
    @NotEmpty
    private List<OrderItemDTO> items;

    public OrderCreationDTO() {
    }

    public OrderCreationDTO(Long customerId, List<OrderItemDTO> items) {
        this.customerId = customerId;
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderCreationDTO{" +
                "customerId=" + customerId +
                ", items=" + items +
                '}';
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
