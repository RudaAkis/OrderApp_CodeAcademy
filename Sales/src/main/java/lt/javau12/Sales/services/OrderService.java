package lt.javau12.Sales.services;

import lt.javau12.Sales.dtos.CustomerDTO;
import lt.javau12.Sales.dtos.OrderCreationDTO;
import lt.javau12.Sales.dtos.OrderDTO;
import lt.javau12.Sales.dtos.OrderItemDTO;
import lt.javau12.Sales.mappers.CustomerMapper;
import lt.javau12.Sales.mappers.OrderItemMapper;
import lt.javau12.Sales.mappers.OrderMapper;
import lt.javau12.Sales.models.Customer;
import lt.javau12.Sales.models.Goods;
import lt.javau12.Sales.models.Order;
import lt.javau12.Sales.models.OrderItem;
import lt.javau12.Sales.repositories.GoodsRepository;
import lt.javau12.Sales.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderService(CustomerService customerService,
                        OrderRepository orderRepository,
                        GoodsRepository goodsRepository,
                        OrderMapper orderMapper,
                        OrderItemMapper orderItemMapper){
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow( () -> new RuntimeException("Order not found"));
        return orderMapper.toDTO(order, order.getOrderItems().stream()
                .map(orderItemMapper::toOrderItemResponseDTO)
                .toList());
    }

    public Order createOrder(OrderCreationDTO orderCreationDTO){
        //Find the customer and if not throw a RuntimeException
        Customer customer = customerService.getCustomerById(orderCreationDTO.getCustomerId());

        Order order = new Order();//Create new order
        order.setCreatedAt(LocalDateTime.now());//Set the date and time for the order
        order.setCustomer(customer);//Set the customer by transforming DTO to Entity from mapper
        //Transform all orderItemDTO's to entities and assign each the order then return a list
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDTO dto : orderCreationDTO.getItems()){
            items.add(createOrderItem(dto, order, items));
        }
        //Add the list of OrderItem entities to the order
        order.setOrderItems(items);
        return orderRepository.save(order);
    }

    public OrderItem createOrderItem(OrderItemDTO dto,Order order, List<OrderItem> items){
        //Find the actual goods based on the ID in the OrderItemDTO from the list
        Goods goods = goodsRepository.findById(dto.getGoodsId())
                .orElseThrow( RuntimeException::new );

        //Create new Order item each cycle to add to the items list
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setPriceAtOrderTime(goods.getPrice());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setGoods(goods);

        return orderItem;
    }


    public boolean delete(Long id){
        if (orderRepository.existsById(id)){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
