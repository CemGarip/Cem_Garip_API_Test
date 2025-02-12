package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.io.IOException;
import static constant.APIConstant.*;
import static constant.APIConstant.FIND_PET_BY_ID_PATH;
import static helper.ResourceHelper.generateStringFromResource;
import static io.restassured.RestAssured.given;
public class Methods {
    public Methods(String baseUri) {
        RestAssured.baseURI = baseUri;
    }
    public Response createPet(String file) throws IOException {
        return given()
                .header("Content-Type","application/json")
                .log()
                .all()
                .body(generateStringFromResource(file))
                .when()
                .post(CREATE_PET_PATH);
    }
    public Response updatePet(String file) throws IOException{
        return given()
                .header("Content-Type","application/json")
                .log()
                .all()
                .body(generateStringFromResource(file))
                .when()
                .put(UPDATE_PET_PATH);
    }
    public Response findPetByStatus(String status){
        return given()
                .header("Accept","application/json")
                .log()
                .all()
                .when()
                .queryParam("status",status)
                .get(FIND_PET_BY_STATUS_PATH);
    }
    public Response petFingById(int petId){
        return given()
                .header("Accept","application/json")
                .log()
                .all()
                .when()
                .get(FIND_PET_BY_ID_PATH + petId);
    }
    public Response petDeleteById(int petId){
        return given()
                .header("Accept","application/json")
                .log()
                .all()
                .when()
                .delete(FIND_PET_BY_ID_PATH + petId);
    }
}
