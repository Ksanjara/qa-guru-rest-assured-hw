package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.emptyString;

public class DeleteUserSpec {
    public static RequestSpecification deleteRequestSpec = with()
            .filter(withCustomTemplates())
            .basePath("/api/users/2");

    public static ResponseSpecification deleteResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .expectBody(emptyString())
            .log(STATUS)
            .log(BODY)
            .build();
}
