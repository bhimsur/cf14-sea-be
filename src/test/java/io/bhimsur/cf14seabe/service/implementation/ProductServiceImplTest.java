package io.bhimsur.cf14seabe.service.implementation;

import io.bhimsur.cf14seabe.dto.AddNewProductRequest;
import io.bhimsur.cf14seabe.dto.BuyProductRequest;
import io.bhimsur.cf14seabe.dto.Metadata;
import io.bhimsur.cf14seabe.entity.Product;
import io.bhimsur.cf14seabe.entity.UserProfile;
import io.bhimsur.cf14seabe.entity.Wallet;
import io.bhimsur.cf14seabe.repository.ProductRepository;
import io.bhimsur.cf14seabe.repository.UserProfileRepository;
import io.bhimsur.cf14seabe.repository.WalletRepository;
import io.bhimsur.cf14seabe.service.WalletHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@Slf4j
@ContextConfiguration(classes = ProductServiceImpl.class)
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl service;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private WalletHistoryService walletHistoryService;

    @MockBean
    private Executor executor;

    private final Metadata metadata = Metadata.builder()
            .ipAddress("10.1.1.1")
            .userAgent("Alamofire")
            .userId(1)
            .build();

    private final UserProfile userProfile = UserProfile.builder()
            .userId(1)
            .nameAlias("")
            .createDate(new Timestamp(System.currentTimeMillis()))
            .password("abc")
            .build();

    @Test
    public void getProductList() {
        when(productRepository.findAll())
                .thenReturn(List.of(Product.builder()
                                .id(1L)
                                .price(BigDecimal.TEN)
                                .createDate(new Timestamp(System.currentTimeMillis()))
                                .userProfileId(userProfile)
                        .build()));
        var response = service.getProductList();
        assertNotNull(response);
    }

    @Test
    public void addNewProduct() {
        when(productRepository.save(any()))
                .thenReturn(Product.builder().id(1L).build());
        var response = service.addNewProduct(AddNewProductRequest.builder()
                .price(BigDecimal.TEN)
                .build(), metadata);
        assertNotNull(response);
    }

    @Test
    public void buyProduct() {
        when(userProfileRepository.getUserProfileByUserId(any()))
                .thenReturn(Optional.of(userProfile));
        when(walletRepository.findWalletByUserProfile(any()))
                .thenReturn(Optional.of(Wallet.builder().amount(BigDecimal.TEN).build()));
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.of(Product.builder()
                                .price(BigDecimal.ZERO)
                        .build()));
        var response = service.buyProduct(BuyProductRequest.builder()
                .productId(1L)
                .build(), metadata);
        assertNotNull(response);
    }
}