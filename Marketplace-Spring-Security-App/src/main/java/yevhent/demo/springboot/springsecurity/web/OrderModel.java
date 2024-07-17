package yevhent.demo.springboot.springsecurity.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderModel {

    private long orderId;
    private String customer;
    private long customerId;
    private String orderDetails;

}
