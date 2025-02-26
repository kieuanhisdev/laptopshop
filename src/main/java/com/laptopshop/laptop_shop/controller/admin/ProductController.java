package com.laptopshop.laptop_shop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptop_shop.domain.Product;
import com.laptopshop.laptop_shop.service.ProductService;
import com.laptopshop.laptop_shop.service.UploadService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getAdminProductPage(Model model) {
        model.addAttribute("products", this.productService.getAllProducts());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getAdminCreateProductPage(Model mode) {
        mode.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(
            Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {
        // valodate
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String imgProduct = this.uploadService.handelSaveUploadFile(file, "product");
        product.setImage(imgProduct);
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        Product product = new Product();
        product.setId(id);
        model.addAttribute("product", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProductPage(Model model, @ModelAttribute("product") Product product) {
        // TODO: process POST request

        // System.out.println("Delete user" + newUser);
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdatePage(Model model, @PathVariable("id") Long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        // model.addAttribute("id", id);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postUserUpdatePage(Model model, @ModelAttribute("product") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        System.out.println("Update product" + product);
        Product product1 = this.productService.getProductById(product.getId());
        if (product1 != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handelSaveUploadFile(file, "product");
                product1.setImage(img);
            }
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setDetailDesc(product.getDetailDesc());
            product1.setShortDesc(product.getShortDesc());
            product1.setQuantity(product.getQuantity());
            product1.setSold(product.getSold());
            product1.setFactory(product.getFactory());
            product1.setTarget(product.getTarget());
            this.productService.handleSaveProduct(product1);
        }

        return "redirect:/admin/product";
    }

}
