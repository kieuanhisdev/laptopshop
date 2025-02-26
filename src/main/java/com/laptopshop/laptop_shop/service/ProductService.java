package com.laptopshop.laptop_shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.laptopshop.laptop_shop.domain.Cart;
import com.laptopshop.laptop_shop.domain.CartDetail;
import com.laptopshop.laptop_shop.domain.Product;
import com.laptopshop.laptop_shop.domain.User;
import com.laptopshop.laptop_shop.repository.CartDetailRepository;
import com.laptopshop.laptop_shop.repository.CartRepository;
import com.laptopshop.laptop_shop.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return this.productRepository.getById(id);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa ? nếu chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới cart
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail
            // tìm product by id

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây chưa ?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                //
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);

                    // update cart (sum);
                    int s = (int) (cart.getSum() + 1);
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            // Tải lại Cart từ database để đảm bảo nó ở trạng thái persistent
            Long cartId = cartDetail.getCart().getId();
            Cart currentCart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new RuntimeException("Cart not found"));

            // Xóa CartDetail
            this.cartDetailRepository.delete(cartDetail);

            // Cập nhật giỏ hàng:
            if (currentCart.getSum() > 1) {
                int s = (int) (currentCart.getSum() - 1);
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                cartRepository.save(currentCart);
            } else {
                // Nếu đây là Cart cuối cùng, xóa Cart và cập nhật session về 0
                cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

}
