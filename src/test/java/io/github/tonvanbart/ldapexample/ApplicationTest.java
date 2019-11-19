package io.github.tonvanbart.ldapexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTest {

    @Test
    public void verifyContextLoads() {
        // no action, this just loads the application context
    }
}
