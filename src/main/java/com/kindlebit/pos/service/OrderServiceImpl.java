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

        // For now we are commenting this code for current requirement

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
                tableTop.get().setStatus("active");
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
            editOrderDb.setTableName(tableName.toLowerCase());
            editOrderDb.setCustomerId(customerId);
            editOrderDb.setGrandTotal(grandTotal);

            orderRepository.save(editOrderDb);

            return editOrderDb;
        }

    }

    // New api for create Customer and place parallelly

/*
    @Override
    public Orders createCustomerAndPlaceOrder(Customer customer, String tableName, String orderType) {

        Orders newOrders=new Orders();
        Optional<Customer> existCustomer= customerRepository.findByPhoneNumber(customer.getPhoneNumber());
        if(!existCustomer.isPresent())
        {
            customer.setCreatedAt(new Date());
          Customer newCustomer =  customerRepository.save(customer);

          Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

            tableTop.get().setStatus("booked");
            tableRepository.save(tableTop.get());


              newOrders.setOrderType(orderType);
              newOrders.setOrderDate(new Date());
              newOrders.setCustomerId(newCustomer.getId());
              newOrders.setStatus("unpaid");
              if(orderType.equals("delivery"))
              {
                  newOrders.setTableName("na");
              }
              else {
                  newOrders.setTableName(tableName);
              }
              newOrders.setGrandTotal(0d);

              Orders ordersResponse = orderRepository.save(newOrders);

              return ordersResponse;



        }

        else {

                newOrders.setOrderType(orderType);
                newOrders.setOrderDate(new Date());
                newOrders.setCustomerId(existCustomer.get().getId());
                newOrders.setStatus("unpaid");
                if(orderType.equals("delivery"))
                {
                    newOrders.setTableName("na");
                }
                else {
                    Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

                    tableTop.get().setStatus("booked");
                    tableRepository.save(tableTop.get());
                    newOrders.setTableName(tableName);
                }
                newOrders.setGrandTotal(0d);

                Orders ordersResponse = orderRepository.save(newOrders);

                return ordersResponse;


        }
    }
*/




    @Override
    public Orders createCustomerAndPlaceOrder(Customer customer, String tableName, String orderType) {

        Orders newOrders=new Orders();
        Optional<Customer> existCustomer= customerRepository.findByPhoneNumber(customer.getPhoneNumber());
        if(!existCustomer.isPresent())
        {
            customer.setCreatedAt(new Date());
            Customer newCustomer =  customerRepository.save(customer);




            newOrders.setOrderType(orderType);
            newOrders.setOrderDate(new Date());
            newOrders.setCustomerId(newCustomer.getId());
            newOrders.setStatus("unpaid");
            if(orderType.equals("delivery"))
            {
                newOrders.setTableName("na");
            }
            else if(orderType.equals("booked")) {
                Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

                tableTop.get().setStatus("booked");
                tableRepository.save(tableTop.get());
                newOrders.setTableName(tableName);
            }
            else if(orderType.equals("dine in")) {
                Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

                tableTop.get().setStatus("active");
                tableRepository.save(tableTop.get());
                newOrders.setTableName(tableName);
            }
            newOrders.setGrandTotal(0d);

            Orders ordersResponse = orderRepository.save(newOrders);

            return ordersResponse;



        }

        else {

            newOrders.setOrderType(orderType);
            newOrders.setOrderDate(new Date());
            newOrders.setCustomerId(existCustomer.get().getId());
            newOrders.setStatus("unpaid");
            if(orderType.equals("delivery"))
            {
                newOrders.setTableName("na");
            }
            else if(orderType.equals("booked"))  {
                Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

                tableTop.get().setStatus("booked");
                tableRepository.save(tableTop.get());
                newOrders.setTableName(tableName);
            }
            else if(orderType.equals("dine in")) {
                Optional<TableTop> tableTop= tableRepository.findByTableName(tableName);

                tableTop.get().setStatus("active");
                tableRepository.save(tableTop.get());
                newOrders.setTableName(tableName);
            }
            newOrders.setGrandTotal(0d);

            Orders ordersResponse = orderRepository.save(newOrders);

            return ordersResponse;


        }
    }



}
