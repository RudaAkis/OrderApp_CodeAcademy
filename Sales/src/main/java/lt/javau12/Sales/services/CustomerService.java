package lt.javau12.Sales.services;

import lt.javau12.Sales.dtos.CustomerDTO;
import lt.javau12.Sales.mappers.CustomerMapper;
import lt.javau12.Sales.models.Customer;
import lt.javau12.Sales.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository
    ,CustomerMapper customerMapper){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAll(){
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow( () -> new RuntimeException("Customer not found"));
    }

    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer entity = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(entity));
    }

    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("No customer found to upload Avatar image") );
        customer.setImage(file.getBytes());
        customerRepository.save(customer);
    }

    public byte[] getAvatar(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Customer not found"));
        return customer.getImage();
    }
}
