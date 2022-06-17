package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.dto.*;
import io.bhimsur.cf14seabe.entity.Product;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.exception.DataNotFoundException;
import io.bhimsur.cf14seabe.exception.InsufficientBalanceException;
import io.bhimsur.cf14seabe.repository.ProductRepository;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import io.bhimsur.cf14seabe.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private WalletRepository walletRepository;

    /**
     * @return GetProductListResponse
     */
    @Override
    public GetProductListResponse getProductList() {
        log.info("start getProductList");
        var result = productRepository.findAll();
        return GetProductListResponse.builder()
                .result(result.stream()
                        .map(data -> GetProductListDto.builder()
                                .id(data.getId())
                                .productName(data.getProductName())
                                .productImage(data.getProductImage())
                                .description(data.getDescription())
                                .price(data.getPrice())
                                .createDate(data.getCreateDate())
                                .userProfileId(data.getUserProfileId().getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * @param request AddNewProductRequest
     * @return BaseResponse
     */
    @Override
    public BaseResponse addNewProduct(AddNewProductRequest request) {
        log.info("start addNewProduct request : {}", request);
        Product product = Product.builder()
                .productName(request.getProductName())
                .productImage(request.getProductImage())
                .description(request.getDescription())
                .price(request.getPrice())
                .createDate(new Timestamp(System.currentTimeMillis()))
                .build();
        return BaseResponse.builder()
                .success(productRepository.save(product).getId() > 1)
                .build();
    }

    /**
     * @param request BuyProductRequest
     * @return BaseResponse
     */
    @Override
    public BaseResponse buyProduct(BuyProductRequest request) {
        log.info("start buyProduct request : {}", request);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(request.getUserProfileId());
        UserProfile userProfile = userProfileOptional.orElseThrow(() -> new DataNotFoundException("User not found"));
        Optional<Wallet> dbWallet = walletRepository.findWalletByUserProfile(userProfile);

        Wallet wallet = dbWallet.orElseGet(() -> walletRepository.save(Wallet.builder()
                .createDate(new Timestamp(System.currentTimeMillis()))
                .amount(BigDecimal.ZERO)
                .userProfile(userProfile)
                .build()));

        Optional<Product> existProduct = productRepository.findById(request.getProductId());
        if (existProduct.isEmpty()) {
            throw new DataNotFoundException("Product not found");
        }
        Product product = existProduct.get();
        if (wallet.getAmount().compareTo(product.getPrice()) < 0) {
            throw new InsufficientBalanceException("insufficient funds");
        }

        product.setDeleted(true);
        wallet.setAmount(wallet.getAmount().subtract(product.getPrice()));
        productRepository.save(product);
        walletRepository.save(wallet);
        return BaseResponse.builder()
                .success(true)
                .build();
    }
}