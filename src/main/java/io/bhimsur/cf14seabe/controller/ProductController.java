package io.bhimsur.cf14seabe.controller;

import io.bhimsur.cf14seabe.dto.AddNewProductRequest;
import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.BuyProductRequest;
import io.bhimsur.cf14seabe.dto.GetProductListResponse;
import io.bhimsur.cf14seabe.service.ProductService;
import io.bhimsur.cf14seabe.util.MetadataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/product")
@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final MetadataUtil metadataUtil;

    public ProductController(ProductService productService, MetadataUtil metadataUtil) {
        this.productService = productService;
        this.metadataUtil = metadataUtil;
    }

    @GetMapping
    public GetProductListResponse getProductList() {
        return productService.getProductList();
    }

    @PostMapping("/add")
    public BaseResponse addNewProduct(@RequestBody AddNewProductRequest request, HttpServletRequest httpServletRequest) {
        return productService.addNewProduct(request, metadataUtil.constructMetadata(httpServletRequest));
    }

    @PostMapping("/buy")
    public BaseResponse buyProduct(@RequestBody BuyProductRequest request, HttpServletRequest httpServletRequest) {
        return productService.buyProduct(request, metadataUtil.constructMetadata(httpServletRequest));
    }

}
