package yevhent.demo.springboot.springsecurity.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="customers")
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="contact_name")
    private String contactName;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phoneNumber;

}
