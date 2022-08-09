package ru.gbank.pabw.core.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gbank.pabw.core.entities.Product;
import ru.gbank.pabw.core.repositories.product.ProductRepository;
import ru.gbank.pabw.model.enums.ProductType;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }
    public Optional<Product> findById(long productId){
        return productRepository.findById(productId);
    }

    public List<Product> findAllCreditTypeProduct(){
        return productRepository.findAllByProductType(ProductType.CREDIT);
    }

    public List<Product> findAllDebitTypeProduct(){
        return productRepository.findAllByProductType(ProductType.DEPOSIT);
    }
}
