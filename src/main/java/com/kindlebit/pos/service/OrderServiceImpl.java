package com.kindlebit.pos.service;

import com.kindlebit.pos.models.Customer;

import com.kindlebit.pos.models.Orders;
import com.kindlebit.pos.models.TableTop;
import com.kindlebit.pos.repository.CustomerRepository;
import com.kindlebit.pos.repository.OrdersRepository;
import com.kindlebit.pos.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrdersRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    TableRepository tableRepository;

    @Override
    public Orders placeOrder(Orders order, Long customerId) {

        Optional<Customer> existingCustomer= customerRepository.findById(customerId);

        if(!existingCustomer.isPresent())
        {
            throw new RuntimeException(" Customer not found ");
        }
        Orders orderDB=new Orders();
        orderDB.setOrderDate(new Date());
        orderDB.setOrderType(order.getOrderType());
        orderDB.setCustomerId(order.getCustomerId());
        orderDB.setStatus("unpaid");
        orderDB.setGrandTotal(0.0);
        if(order.getOrderType().toLowerCase().equals("delivery")) {
            orderDB.setTableName("NA");
        }
        else {

            Optional<TableTop> tableTop= tableRepository.findByTableName(order.getTableName());
            if(!tableTop.isPresent())
            {
                throw new RuntimeException(" Table name not found !! ");
            }else {
                tableTop.get().setStatus("booked");
                tableRepository.save(tableTop.get());
                orderDB.setTableName(order.getTableName());
            }

        }
        orderDB.setCustomerId(customerId);
        orderRepository.save(orderDB);
        return orderDB;
    }

    @Override
    public Orders getOrder(Long orderId) {

        Optional<Orders> existedOrder=orderRepository.findById(orderId);
        if(!existedOrder.isPresent())
        {
            throw new RuntimeException(" Order not found ");
        }
        return existedOrder.get();
    }


}
