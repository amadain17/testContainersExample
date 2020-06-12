package ie.amach.testContainersExample;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ContainersAT {
    private RequestSpecification requestSpecification;

    @ClassRule
    public static GenericContainer genericContainer = new GenericContainer("test-containers-example:latest")
            .withExposedPorts(8080)
            .waitingFor(Wait.forHttp("/employees"));


    @Before
    public void setUp() {
        String address = String.format("http://%s:%s", genericContainer.getContainerIpAddress()
                , genericContainer.getFirstMappedPort());

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
