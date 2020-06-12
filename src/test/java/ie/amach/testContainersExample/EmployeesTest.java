package ie.amach.testContainersExample;

import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class EmployeesTest extends BaseClass{
    static final String BASE_PATH = "/employees";

    @Test
    public void verifyGetRequest() {
        JsonPath response = given().spec(requestSpecification).basePath(BASE_PATH)
                .when().get()
                .then().statusCode(SC_OK).extract().jsonPath();
        int employeeListSize = ((List) response.get("_embedded.employeeList")).size();
        assertThat(employeeListSize).isEqualTo(2);
    }
}
