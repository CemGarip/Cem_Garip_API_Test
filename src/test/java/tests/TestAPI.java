package tests;

import utils.Assertions;
import utils.Methods;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static constant.APIConstant.*;

public class TestAPI {
    private static final Logger logger = LoggerFactory.getLogger(TestAPI.class);
    private static Methods methods;
    private static Assertions assertions;

    @Before
    public void setup() {
        logger.info("Initializing API test setup...");
        methods = new Methods(BASEURL);
        assertions = new Assertions(BASEURL);
        logger.info("API test execution starting");
    }

    Response response;
    JsonPath responseJson;

    @Test
    public void createPetSuccessfully() throws IOException {
        response = methods.createPet("PetCreateSuccess");
        responseJson = new JsonPath(response.asString());
        logger.info("Sending request to create a new pet...");
        assertions.checkStatusCode(response, 200);
        assertions.nullCheck(response, "id");
        assertions.nullCheck(response, "photoUrls[0]");
        assertions.nullCheck(response, "tags");
        assertions.checkParameterStringValue(response, "name", CREATE_PET_NAME);
        assertions.checkParameterStringValue(response, "status", CREATE_PET_STATUS);
        assertions.checkParameterStringValue(response, "category.name", (String) CREATE_PET_CATEGORY.get("name"));
        Assert.assertEquals(responseJson.getString("category.name"), CREATE_PET_CATEGORY.get("name"));
        logger.info("Pet created successfully with expected attributes.");
    }

    @Test
    public void createPetWithInvalidData() throws IOException {
        response = methods.createPet("PetCreate400");
        logger.info("Sending request with invalid pet data...");
        assertions.checkStatusCode(response, 400);
        assertions.checkParameterStringValue(response, "message", CREATE_PET_400_MESSAGE);
        assertions.checkParameterIntegerValue(response, "code", 400);
        logger.info("Received expected 400 Bad Request response.");
    }

    @Test
    public void updatePetSuccessfully() throws IOException {
        response = methods.updatePet("PetUpdateSuccess");
        responseJson = new JsonPath(response.asString());
        logger.info("Sending request to update an existing pet...");
        assertions.checkStatusCode(response, 200);
        assertions.nullCheck(response, "id");
        assertions.nullCheck(response, "photoUrls[0]");
        assertions.nullCheck(response, "tags");
        assertions.checkParameterStringValue(response, "name", UPDATE_PET_NAME);
        assertions.checkParameterStringValue(response, "status", UPDATE_PET_STATUS);
        assertions.checkParameterStringValue(response, "category.name", (String) UPDATE_PET_CATEGORY.get("name"));
        Assert.assertEquals(responseJson.get("photoUrls"), UPDATE_PET_PHOTOS);
        logger.info("Pet updated successfully with expected attributes.");
    }

    @Test
    public void updatePetWithInvalidData() throws IOException {
        response = methods.updatePet("PetUpdate400");
        logger.info("Sending update request with invalid data...");
        assertions.checkStatusCode(response, 400);
        assertions.checkParameterIntegerValue(response, "code", 400);
        assertions.checkParameterStringValue(response, "message", UPDATE_PET_400_MESSAGE);
        logger.info("Received expected 400 Bad Request response.");
    }

    @Test
    public void findAvailablePets() {
        response = methods.findPetByStatus(STATUS_AVAILABLE);
        responseJson = new JsonPath(response.asString());
        logger.info("Fetching pets with 'available' status...");
        assertions.checkStatusCode(response, 200);

        for (int i = 1; i < responseJson.getInt("$.size()"); i++) {
            assertions.checkParameterStringValue(response, "[" + i + "].status", STATUS_AVAILABLE);
            assertions.nullCheck(response, "[" + i + "].id");
        }
        logger.info("Successfully retrieved available pets.");
    }

    @Test
    public void findPetById() {
        response = methods.petFingById(10);
        logger.info("Fetching pet by ID: 10");
        assertions.checkStatusCode(response, 200);
        assertions.checkParameterIntegerValue(response, "id", 10);
        logger.info("Successfully retrieved pet details for ID: 10");
    }

    @Test
    public void deletePetByIdSuccessfully() {
        response = methods.petDeleteById(5);
        logger.info("Sending delete request for pet with ID: 5");
        assertions.checkStatusCode(response, 200);
        assertions.checkParameterStringValue(response, "message", "5");
        assertions.checkParameterStringValue(response, "code", "200");
        logger.info("Pet deleted successfully.");
    }

    @Test
    public void deleteNonExistentPet() {
        response = methods.petDeleteById(99999);
        logger.info("Attempting to delete a non-existent pet...");
        assertions.checkStatusCode(response, 404);
        logger.info("Received expected 404 Not Found response.");
    }

    @Test
    public void deletePetWithInvalidId() {
        logger.error("Attempting to delete pet with an invalid ID format.");
    }
}
