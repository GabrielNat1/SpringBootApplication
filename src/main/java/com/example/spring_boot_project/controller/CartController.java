package com.example.spring_boot_project.controller;

import com.example.spring_boot_project.model.Cart;
import com.example.spring_boot_project.model.Product;
import com.example.spring_boot_project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PostMapping("/{cartId}/add")
    public void addProductToCart(@PathVariable Long cartId, @RequestBody Product product, @RequestParam int quantity) {
        cartService.addProductToCart(cartId, product, quantity);
    }

    @DeleteMapping("/{cartId}/remove/{productId}")
    public void removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.removeProductFromCart(cartId, productId);
    }
}