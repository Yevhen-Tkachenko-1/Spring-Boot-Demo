package yevhent.demo.springboot.springsecurity.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import yevhent.demo.springboot.springsecurity.data.model.Customer;
import yevhent.demo.springboot.springsecurity.data.model.Order;
import yevhent.demo.springboot.springsecurity.data.repository.CustomerRepository;
import yevhent.demo.springboot.springsecurity.data.repository.OrderRepository;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @GetMapping
    public String getAllOrders(Model model){
        Iterable<Order> orderIterable = this.orderRepository.findAll();
        List<OrderModel> orders = new ArrayList<>();
        orderIterable.forEach(oi ->{
           OrderModel order = new OrderModel();
           order.setOrderId(oi.getId());
           order.setCustomerId(oi.getCustomerId());
           Optional<Customer> oc = this.customerRepository.findById(oi.getCustomerId());
           order.setCustomer(oc.get().getName());
           order.setOrderDetails(oi.getOrderInfo());
           orders.add(order);
        });
        model.addAttribute("orders", orders);
        model.addAttribute("module", "orders");
        return "orders";
    }
}
