package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;


public class Assertions extends Methods {

    public Assertions(String baseUri) {
        super(baseUri);
    }

    public void nullCheck(Response response, String parameter){
        JsonPath responseJson = new JsonPath(response.asString());
        Assert.assertNotNull(parameter + " is null",responseJson.getString(parameter));
    }
    public void checkStatusCode(Response response,int statusCode){
        Assert.assertEquals(statusCode,response.getStatusCode());
    }
    public void checkParameterStringValue(Response response,String parameter,String expectedValue){
        JsonPath responseJson = new JsonPath(response.asString());
        Assert.assertEquals(expectedValue,responseJson.getString(parameter));
    }
    public void checkParameterIntegerValue(Response response,String parameter,int expectedValue){
        JsonPath responseJson = new JsonPath(response.asString());
        Assert.assertEquals(expectedValue,responseJson.getInt(parameter));
    }
}
