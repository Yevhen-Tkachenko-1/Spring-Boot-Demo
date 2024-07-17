package yevhent.demo.springboot.springsecurity.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yevhent.demo.springboot.springsecurity.data.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
