package com.zyc.beancopy;

import com.github.dozermapper.core.Mapper;
import com.zyc.beancopy.dto.NotSameAttributeA;
import com.zyc.beancopy.dto.NotSameAttributeB;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DozerApp.class)
public class DozerTest extends TestCase {
    @Autowired
    private Mapper mapper;

    @Test
    public void testNotSameAttributeMapping() {
        NotSameAttributeA src = new NotSameAttributeA();
        src.setId(007);
        src.setName("邦德");
        src.setDate(new Date());

        NotSameAttributeB desc = mapper.map(src, NotSameAttributeB.class);
        Assert.assertNotNull(desc);
    }
}

