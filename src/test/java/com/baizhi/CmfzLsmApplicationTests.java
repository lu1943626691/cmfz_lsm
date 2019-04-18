package com.baizhi;

import com.baizhi.service.BannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzLsmApplicationTests {
    @Autowired
    private BannerService bannerService;

    @Test
    public void contextLoads() {

    }

}
