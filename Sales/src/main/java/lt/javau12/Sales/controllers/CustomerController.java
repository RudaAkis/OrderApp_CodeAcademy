package lt.javau12.Sales.controllers;

import lt.javau12.Sales.models.Customer;
import lt.javau12.Sales.services.CustomerService;
import lt.javau12.Sales.dtos.CustomerDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/all")  //Test URL localhost:8080/customers/all
    public ResponseEntity<List<CustomerDTO>> getAll(){
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")//Test URL localhost:8080/customers/1
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping//Test URL localhost:8080/customers
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO){
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(customerService.create(customerDTO));
    }

    @PostMapping("/{id}/avatar")
    //Here we use the MultiPartFile and we need to use @RequestParam instead of
    // @RequestBody since we need larger file not the whole body
    public ResponseEntity<Void> uploadAvatarPhoto(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        customerService.uploadAvatar(id, file);
        return ResponseEntity.ok().build();
    }

        @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id){
        byte [] image = customerService.getAvatar(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

}
