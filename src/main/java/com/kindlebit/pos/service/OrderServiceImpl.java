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
                tableTop.get().setStatus(" active ");
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

    @Override
    public Orders editOrder(Orders order,Long orderId) {


        Optional<Orders> orderDb = orderRepository.findById(orderId);

        if(!orderDb.isPresent())
        {
            throw new RuntimeException(" Order Id not found ");
        }
        else{

            Orders editOrderDb = new Orders();
            Date orderDate = ( order.getOrderDate() == null ? orderDb.get().getOrderDate():order.getOrderDate());
            Long customerId = ( order.getCustomerId() == null ? orderDb.get().getCustomerId() :order.getCustomerId());
            String orderType = ( order.getOrderType()== null || order.getOrderType()==" " ? orderDb.get().getOrderType():order.getOrderType() );
            String tableName =(order.getTableName()== null || order.getTableName()==" "?orderDb.get().getTableName():order.getTableName());
            Double grandTotal =(order.getGrandTotal()==0 ? orderDb.get().getGrandTotal() : order.getGrandTotal() ) ;

            String status = ( order.getStatus()== null || order.getStatus()==" " ? orderDb.get().getStatus():order.getStatus());

            editOrderDb.setId(orderDb.get().getId());
            editOrderDb.setOrderDate(orderDate);
            editOrderDb.setOrderType(orderType);
            editOrderDb.setStatus(status);
            editOrderDb.setTableName(tableName);
            editOrderDb.setCustomerId(customerId);
            editOrderDb.setGrandTotal(grandTotal);

            orderRepository.save(editOrderDb);

            return editOrderDb;
        }

    }


}
