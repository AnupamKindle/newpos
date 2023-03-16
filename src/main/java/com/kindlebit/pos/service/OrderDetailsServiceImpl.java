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
        Recipe recipe = recipeRepository.findByName(recipeName.toLowerCase());
        Integer halfPrice = recipe.getHalfPrice();
        Integer fullPrice = recipe.getFullPrice();
        Integer halfQuantity = orderDetails.getHalfQuantity();
        Integer fullQuantity = orderDetails.getFullQuantity();
        Integer totalAmount = (halfPrice * halfQuantity)+(fullPrice * fullQuantity );

        Optional<OrderDetails> orderDetailsExistOb=orderDetailsRepository.findByRecipeName(recipeName.toLowerCase(),orderId);
        if(orderDetailsExistOb.isPresent())
        {
            orderDetailsDB.setId(orderDetailsExistOb.get().getId());
        }
        orderDetailsDB.setFullQuantity(orderDetails.getFullQuantity());
        orderDetailsDB.setHalfQuantity(orderDetails.getHalfQuantity());
        orderDetailsDB.setOrderId(orders.get().getId());
        orderDetailsDB.setTotalAmount(totalAmount);
        orderDetailsDB.setRecipeName(recipeName.toLowerCase());
        orderDetailsRepository.save(orderDetailsDB);
        return orderDetailsDB;
    }

    @Override
    public InvoiceDTO invoice(Long orderId, Double discount) {

        InvoiceDTO invoiceDTO=new InvoiceDTO();
        Double totalSum = Double.valueOf(orderDetailsRepository.totalAmount(orderId));
        
        Double gstAmount = (totalSum * 18/100)/100;


        totalSum = totalSum + gstAmount;

        discount = totalSum  * (discount/100);
        
        Double amountToBePaid =  totalSum - discount ;

        List<OrderDetails> orderDetailsList = orderDetailsRepository.listOfOrderItem(orderId);

        Optional<Orders> orders= orderRepository.findById(orderId);

        orders.get().setStatus("paid");
        orders.get().setGrandTotal(amountToBePaid);

        orderRepository.save(orders.get());
        String tableName = orders.get().getTableName();

        //System.out.println("=========================================>>>"+tableName);
        if( (!(tableName.equals("NA"))) || (!(tableName.equals("na"))) ){
            Optional<TableTop> tableTop = tableRepository.findByTableName(tableName);
            tableTop.get().setStatus("free");

            tableRepository.save(tableTop.get());
        }

        //for now we are commenting customer ID
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

    @Override
    public OrderDetails editEntryInOrder(Long orderDetailsId, OrderDetails orderDetails) {

        Optional<OrderDetails> orderDetailsExist = orderDetailsRepository.findById(orderDetailsId);
        Integer totalAmount=0;
        OrderDetails editOrderDetails=new OrderDetails();
        if(!orderDetailsExist.isPresent())
        {
            throw new RuntimeException("  Order Details Id not found  ");
        }
        else {
            String recipeName = (orderDetails.getRecipeName()==" " && orderDetails.getRecipeName()==null ? orderDetailsExist.get().getRecipeName():orderDetails.getRecipeName());

            Recipe existRecipe =recipeRepository.findByName(recipeName.toLowerCase());
            if(existRecipe==null)
            {
                throw new RuntimeException("Not valid recipe name");
            }
            Long orderId = (orderDetails.getOrderId()==0 ?orderDetailsExist.get().getOrderId():orderDetails.getOrderId());

            Integer halfQuantity =(orderDetails.getHalfQuantity() ==0 ? orderDetailsExist.get().getHalfQuantity():orderDetails.getHalfQuantity());

            Integer fullQuantity =(orderDetails.getFullQuantity() !=0 ? orderDetails.getFullQuantity():orderDetailsExist.get().getFullQuantity());

            if(halfQuantity !=0 && fullQuantity!=0)
            {
                 totalAmount =(halfQuantity* existRecipe.getHalfPrice()+fullQuantity*existRecipe.getFullPrice());
            }else {
                 totalAmount = (orderDetails.getTotalAmount() != 0 ? orderDetails.getTotalAmount() : orderDetailsExist.get().getTotalAmount());
            }
            editOrderDetails.setId(orderDetailsExist.get().getId());
            editOrderDetails.setRecipeName(recipeName);
            editOrderDetails.setHalfQuantity(halfQuantity);
            editOrderDetails.setFullQuantity(fullQuantity);
            editOrderDetails.setTotalAmount(totalAmount);
            editOrderDetails.setOrderId(orderId);

            orderDetailsRepository.save(editOrderDetails);
            return editOrderDetails;

        }




    }

    @Override
    public List<OrderDetails> allEntryAccordingToOrderId(Long orderId) {

        List<OrderDetails> orderDetailsList = orderDetailsRepository.listOfOrderItem(orderId);
        return orderDetailsList;
    }

    @Override
    public Boolean deleteItemPresentInCart(Long orderDetailsId) {

        Optional<OrderDetails> orderDetails=orderDetailsRepository.findById(orderDetailsId);
        if(!orderDetails.isPresent())
        {
            throw new RuntimeException(" Id not found ");
        }
        else {
          orderDetailsRepository.delete(orderDetails.get());
            Boolean response=true;
            return response;
        }
    }


}
