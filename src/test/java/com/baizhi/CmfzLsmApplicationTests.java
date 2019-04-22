package com.baizhi;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzLsmApplicationTests {
    @Autowired
    private AlbumService albumService;

    @Test
    public void contextLoads() {
        List<Album> albums = albumService.selectAll();
        System.out.println(albums);
    }

    @Test
    public void aa() {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-30adc5117b494389ae69e971d734008a");
        goEasy.publish("lsm", "Hello, lsm!");
    }

}
