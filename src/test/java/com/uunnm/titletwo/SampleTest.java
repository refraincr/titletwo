package com.uunnm.titletwo;

import com.uunnm.titletwo.business.record.bo.PersonQueryBO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class SampleTest {
    @Test
    public void testSelect() {
        Assert.isTrue(
                new PersonQueryBO("n", "123").equals(new PersonQueryBO("n", "123")),
                "对象相等"
        );
    }
}
