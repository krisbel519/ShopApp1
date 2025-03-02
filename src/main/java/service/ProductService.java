package service;

import model.Product;
import repository.ProductRepository;
import javax.inject.Inject;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    @Inject
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void listProducts() {
        List<Product> products = productRepository.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("Brak dostępnych produktów.");
        } else {
            System.out.println("Lista produktów:");
            products.forEach(System.out::println);
        }
    }

    public void addProduct(String name, double price, int quantity) {
        productRepository.addProduct(name, price, quantity);
        System.out.println("Produkt dodany: " + name);
    }
}
