package io.bhimsur.cf14seabe;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Cf14SeaBeApplicationTest {

    @Test
    public void contextLoads() {
        var angka = 10000;
        var expektasi = "10.000,00";
        log.info(expektasi);
    }

}