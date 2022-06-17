package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.AddNewProductRequest;
import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.BuyProductRequest;
import io.bhimsur.cf14seabe.dto.GetProductListResponse;
import io.bhimsur.cf14seabe.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product")
@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public GetProductListResponse getProductList() {
        return productService.getProductList();
    }

    @PostMapping("/add")
    public BaseResponse addNewProduct(@RequestBody AddNewProductRequest request) {
        return productService.addNewProduct(request);
    }

    @PostMapping("/buy")
    public BaseResponse buyProduct(@RequestBody BuyProductRequest request) {
        return productService.buyProduct(request);
    }

}
