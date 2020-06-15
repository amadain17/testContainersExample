package ie.amach.testContainersExample;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ContainersAT {
    @ClassRule
    public static GenericContainer genericContainer = new GenericContainer("test-containers-example:latest")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/employees")
                    .withStartupTimeout(Duration.ofSeconds(120)));
    static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
    private RequestSpecification requestSpecification;

    @Before
    public void setUp() {
        genericContainer.followOutput(logConsumer);
        String address = String.format("http://%s:%s", genericContainer.getHost(),
                genericContainer.getMappedPort(8080));

        requestSpecification = new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
                .setBaseUri(address)
                .build();
    }

    @Test
    public void verifyGetRequest() {
        JsonPath jsonPath = given().spec(requestSpecification)
                .when().get("/employees")
                .then().statusCode(SC_OK).extract().jsonPath();

        int employeeListSize = jsonPath.getList("_embedded.employeeList").size();
        assertThat(employeeListSize).isEqualTo(2);
    }
}
