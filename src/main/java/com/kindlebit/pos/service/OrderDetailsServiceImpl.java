package com.kindlebit.pos.service;


import com.kindlebit.pos.models.OrderDetails;
import com.kindlebit.pos.models.Orders;
import com.kindlebit.pos.models.Recipe;
import com.kindlebit.pos.repository.OrderDetailsRepository;
import com.kindlebit.pos.repository.OrdersRepository;
import com.kindlebit.pos.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailsServiceImpl implements  OrderDetailsService{


    @Autowired
    OrdersRepository orderRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;


    @Override
    public OrderDetails createOrder(Long orderId, OrderDetails orderDetails) {

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
        orderDetailsRepository.save(orderDetailsDB);

        return orderDetailsDB;
    }
}
