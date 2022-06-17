package io.bhimsur.cf14seabe.service;

import io.bhimsur.cf14seabe.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    GetProductListResponse getProductList();

    BaseResponse addNewProduct(AddNewProductRequest request, Metadata metadata);

    BaseResponse buyProduct(BuyProductRequest request, Metadata metadata);
}
