package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.constant.TransactionType;
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
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private WalletHistoryService walletHistoryService;

    /**
     * @return GetProductListResponse
     */
    @Override
    public GetProductListResponse getProductList() {
        log.info("start getProductList");
        try {
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
                                    .userProfile(data.getUserProfileId())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param request  AddNewProductRequest
     * @param metadata Metadata
     * @return BaseResponse
     */
    @Override
    @Transactional
    public BaseResponse addNewProduct(AddNewProductRequest request, Metadata metadata) {
        log.info("start addNewProduct request : {}", request);
        try {
            Product product = Product.builder()
                    .productName(request.getProductName())
                    .productImage(request.getProductImage())
                    .description(request.getDescription())
                    .price(request.getPrice())
                    .createDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            return BaseResponse.builder()
                    .success(productRepository.save(product).getId() > 0)
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }

    /**
     * @param request  BuyProductRequest
     * @param metadata Metadata
     * @return BaseResponse
     */
    @Override
    @Transactional
    public BaseResponse buyProduct(BuyProductRequest request, Metadata metadata) {
        log.info("start buyProduct request : {}, metadata : {}", request, metadata);
        try {
            Optional<UserProfile> userProfileOptional = userProfileRepository.getUserProfileByUserId(metadata.getUserId());
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

            new Thread(() -> walletHistoryService.store(StoreWalletHistoryRequest.builder()
                    .transactionType(TransactionType.BUY)
                    .amount(product.getPrice())
                    .userProfile(userProfile)
                    .wallet(wallet)
                    .build()));
            return BaseResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            log.error("error Exception : {}, caused by : {}", e.getMessage(), e.getCause());
            throw e;
        }
    }
}
