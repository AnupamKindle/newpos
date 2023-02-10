package com.kindlebit.pos.service;


import com.kindlebit.pos.dto.InvoiceDTO;
import com.kindlebit.pos.models.*;
import com.kindlebit.pos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements  OrderDetailsService{


    @Autowired
    OrdersRepository orderRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    TableRepository tableRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Override
    public OrderDetails entryInOrder(Long orderId, OrderDetails orderDetails) {

        Optional<Orders> orders=orderRepository.findById(orderId);

        OrderDetails orderDetailsDB  = new OrderDetails();
        if(!orders.isPresent())
        {
            throw new RuntimeException(" Order not found ");
        }
        String recipeName= orderDetails.getRecipeName();
        Recipe recipe = recipeRepository.findByName(recipeName);
        Integer halfPrice = recipe.getHalfPrice();
        Integer fullPrice = recipe.getFullPrice();
        Integer halfQuantity = orderDetails.getHalfQuantity();
        Integer fullQuantity = orderDetails.getFullQuantity();
        Integer totalAmount = (halfPrice * halfQuantity)+(fullPrice * fullQuantity );
        orderDetailsDB.setFullQuantity(orderDetails.getFullQuantity());
        orderDetailsDB.setHalfQuantity(orderDetails.getHalfQuantity());
        orderDetailsDB.setOrderId(orders.get().getId());
        orderDetailsDB.setTotalAmount(totalAmount);
        orderDetailsDB.setRecipeName(recipeName);
        orderDetailsRepository.save(orderDetailsDB);
        return orderDetailsDB;
    }

    @Override
    public InvoiceDTO invoice(Long orderId, Double discount) {

        InvoiceDTO invoiceDTO=new InvoiceDTO();
        Double totalSum = Double.valueOf(orderDetailsRepository.totalAmount(orderId));
        
        Double gstAmount = (totalSum * 18/100)/100;

        System.out.println("======================================>>>>"+gstAmount +"     total Sum   "+ totalSum);
        totalSum = totalSum + gstAmount;

        discount = totalSum  * (discount/100);
        
        Double amountToBePaid =  totalSum - discount ;

        List<OrderDetails> orderDetailsList = orderDetailsRepository.listOfOrderItem(orderId);

        Optional<Orders> orders= orderRepository.findById(orderId);

        orders.get().setStatus("paid");
        orders.get().setGrandTotal(amountToBePaid);

        orderRepository.save(orders.get());
        String tableName = orders.get().getTableName();

       Optional<TableTop> tableTop = tableRepository.findByTableName(tableName);
        tableTop.get().setStatus("free");
        tableRepository.save(tableTop.get());

        Customer customer= customerRepository.findById(orders.get().getCustomerId()).get();
        invoiceDTO.setInvoiceDate(new Date());
        invoiceDTO.setOrderDetails(orderDetailsList);
        invoiceDTO.setStatus("paid");
        invoiceDTO.setAmountToBePaid(amountToBePaid);
        invoiceDTO.setTableName(tableName);
        invoiceDTO.setCustomerName(customer.getName());
        invoiceDTO.setDiscount(discount);


        return invoiceDTO;
    }


}
