package yevhent.demo.springboot.springsecurity.web;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import yevhent.demo.springboot.springsecurity.data.model.Customer;
import yevhent.demo.springboot.springsecurity.data.model.Order;
import yevhent.demo.springboot.springsecurity.data.repository.CustomerRepository;
import yevhent.demo.springboot.springsecurity.data.repository.OrderRepository;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public String getAllUsers(Model model) {
        Iterable<Customer> customersIterable = this.customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        customersIterable.forEach(customers::add);
        customers.sort(Comparator.comparing(Customer::getName));
        model.addAttribute("customers", customers);
        model.addAttribute("module", "customers");
        return "customers";
    }

    @GetMapping(path = "/{id}")
    public String getUser(@PathVariable("id") long customerId, Principal principal, Model model) {
        Optional<Customer> customer = this.customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        model.addAttribute("customer", customer.get());
        List<Order> orders = getOrders(principal, customer.get());
        model.addAttribute("orders", orders);
        model.addAttribute("module", "customers");
        return "detailed_customer";
    }

    private List<Order> getOrders(Principal principal, Customer customer) {
        List<Order> orderList = new ArrayList<>();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            AtomicBoolean auth = new AtomicBoolean(false);
            Collection<GrantedAuthority> authorities =
                    ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
            authorities.forEach(authority -> {
                        if (authority.getAuthority().equals("ROLE_ADMIN")) {
                            auth.set(true);
                        }
                    }
            );
            if (auth.get()) {
                Iterable<Order> orders = this.orderRepository.findAllByCustomerId(customer.getId());
                orders.forEach(orderList::add);
            }
        }
        return orderList;
    }
}
