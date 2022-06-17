package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.AddNewProductRequest;
import io.bhimsur.cf14seabe.dto.BaseResponse;
import io.bhimsur.cf14seabe.dto.BuyProductRequest;
import io.bhimsur.cf14seabe.dto.GetProductListResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    GetProductListResponse getProductList();

    BaseResponse addNewProduct(AddNewProductRequest request);

    BaseResponse buyProduct(BuyProductRequest request);
}
