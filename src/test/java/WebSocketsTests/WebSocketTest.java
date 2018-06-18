package WebSocketsTests;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
    }
    
    @Test
    public void getMainPage() throws Exception {
    	this.base = new URL("http://localhost:" + port + "/mainPage.html");
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from main page!"));
    }
    @Test
    public void getStoresPage() throws Exception {
    	this.base = new URL("http://localhost:" + port + "/storesPage.html");
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Stores page!"));
    }
    @Test
    public void getStorePage() throws Exception {
    	this.base = new URL("http://localhost:" + port + "/storePage.html");
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Store Page!"));
    }
    @Test
    public void getProductPage() throws Exception {
    	this.base = new URL("http://localhost:" + port + "/productPage.html");
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Product Page!"));
    }
}
