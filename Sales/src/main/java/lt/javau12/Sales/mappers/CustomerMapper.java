package lt.javau12.Sales.mappers;

import lt.javau12.Sales.dtos.CustomerDTO;
import lt.javau12.Sales.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customerEntity){
        return new CustomerDTO(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                "/customers/"+customerEntity.getId()+"/avatar"
        );
    }

    public Customer toEntity(CustomerDTO customerDTO){
        return new Customer(
                customerDTO.getName(),
                customerDTO.getEmail()
        );
    }

    public Customer toEntity(Long id, CustomerDTO customerDTO){
        return new Customer(
                id,
                customerDTO.getName(),
                customerDTO.getEmail()
        );
    }
}
