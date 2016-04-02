package de.saphijaga.spoozer.test.cucumber;

import de.saphijaga.spoozer.test.application.SpoozerTestApplication;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by samuel on 06.12.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpoozerTestApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
public abstract class SpringBootTest {
    protected static WebDriver driver;
}