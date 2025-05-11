package lt.javau12.Sales.controllers;

import jakarta.validation.Valid;
import lt.javau12.Sales.dtos.OrderCreationDTO;
import lt.javau12.Sales.dtos.OrderDTO;
import lt.javau12.Sales.models.Order;
import lt.javau12.Sales.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // This is one of the best Loggers for Spring from slf4j package
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        logger.error("Error object with id: " + id + " not found");
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreationDTO orderCreationDTO){
        logger.debug("Got OrderCreationDTO{}", orderCreationDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(orderService.createOrder(orderCreationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        logger.error("Error object with id: " + id + " not found");
        return orderService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
