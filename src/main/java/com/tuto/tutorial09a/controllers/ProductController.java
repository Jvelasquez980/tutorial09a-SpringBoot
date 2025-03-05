package com.tuto.tutorial09a.controllers;

import com.tuto.tutorial09a.models.Comment;
import com.tuto.tutorial09a.models.Product;
import com.tuto.tutorial09a.repositories.CommentRepository;
import com.tuto.tutorial09a.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;

    // Muestra los detalles del producto + comentarios
    @GetMapping("/products/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("title", product.getName() + " - Online Store");
        model.addAttribute("product", product);
        model.addAttribute("newComment", new Comment());

        return "product/show"; // Retorna a la vista del producto
    }

    // Guardar un nuevo comentario
    @PostMapping("/products/{id}/comments")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute Comment comment) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        comment.setProduct(product); // Asocia el comentario al producto
        commentRepository.save(comment); // Guarda el comentario

        return "redirect:/products/" + id; // Redirige a la vista del producto
    }

    @GetMapping("/products")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("title", "Products - Online Store");
        model.addAttribute("subtitle", "List of products");
        model.addAttribute("products", products);
        return "product/index";
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("client", new Product());
        return "product/create";
    }

    @PostMapping("/products")
    public String save(Product product) {
        // Validaciones mínimas
        if (product.getName() == null || product.getName().isEmpty() ||
                product.getPrice() == null) {
            throw new RuntimeException("Name and Price are required");
        }
        productRepository.save(product);
        return "redirect:/products";
    }

}
