package com.tuto.tutorial09a.controllers;

import com.tuto.tutorial09a.models.Product;
import com.tuto.tutorial09a.repositories.ProductRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductRepository productRepository;

    public CartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        // Obtener productos del carrito almacenados en la sesión
        Map<Integer, Integer> cartProductData = (Map<Integer, Integer>) session.getAttribute("cart_product_data");
        Map<Long, Product> cartProducts = new HashMap<>();

        if (cartProductData != null) {
            for (Integer id : cartProductData.keySet()) {
                productRepository.findById(id.longValue()).ifPresent(product -> cartProducts.put(id.longValue(), product));
            }
        }

        model.addAttribute("title", "Cart - Online Store");
        model.addAttribute("subtitle", "Shopping Cart");
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cartProducts", cartProducts);

        return "cart/index";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Integer id, HttpSession session) {
        // Verifica si el producto existe en la base de datos
        Optional<Product> productOpt = productRepository.findById(id.longValue());
        if (productOpt.isEmpty()) {
            return "redirect:/cart"; // Si no existe, redirige al carrito
        }

        // Recupera el carrito o crea uno nuevo
        Map<Integer, Integer> cartProductData = (Map<Integer, Integer>) session.getAttribute("cart_product_data");
        if (cartProductData == null) {
            cartProductData = new HashMap<>();
        }

        // Agregar producto (aumenta la cantidad si ya está en el carrito)
        cartProductData.put(id, cartProductData.getOrDefault(id, 0) + 1);
        session.setAttribute("cart_product_data", cartProductData);

        return "redirect:/cart";
    }

    @GetMapping("/removeAll")
    public String removeAll(HttpSession session) {
        // Elimina el atributo del carrito de la sesión
        session.removeAttribute("cart_product_data");
        return "redirect:/cart";
    }
}
