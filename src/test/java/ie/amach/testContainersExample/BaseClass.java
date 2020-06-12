package ie.amach.testContainersExample;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
//@Testcontainers
public class BaseClass {
    RequestSpecification requestSpecification;
    static final String BASE_URL = "http://localhost:8080";
    //@Container

    //public static GenericContainer employeeContainer = new GenericContainer<>("alpine:3.5")
    //        .withExposedPorts(80)
    //        .
    //String address = "http://"
    //        + employeeContainer.getContainerIpAddress()
    //        + ":" + employeeContainer.getMappedPort(80);

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .setBaseUri(BASE_URL)
                .build();
    }
}
