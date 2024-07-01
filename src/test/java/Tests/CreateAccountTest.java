package Tests;

import ObjectData.request.RequestAccount;
import ObjectData.response.ResponseAccountSucces;
import ObjectData.response.ResponseTokenSucces;
import PropertiesUtillity.PropertiesUtillity;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAccountTest {

    public RequestAccount requestAccountBody;
    public String token;
    public String userID;

    @Test
    public void metodaTest() {
        System.out.println("=====1:CREATE NEW ACCOUNT=====");
        createAccount();
        System.out.println("=====2:GENERATE TOKEN=====");
        generateToken();
        System.out.println("=====3:CHECK ACCOUNT=====");
        checkAccPresence();
        System.out.println("=====4:DELETE ACCOUNT=====");
        deleteUser();
        System.out.println("=====5:RECHECK ACCOUNT=====");
        checkAccPresence();

    }

    public void createAccount() {
        //configuram clientul
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com/");
        requestSpecification.contentType("application/json");
        //pregatim requestul

        PropertiesUtillity propertiesUtillity = new PropertiesUtillity("Request/CreateAccountData");
        requestAccountBody = new RequestAccount(propertiesUtillity.getAllData());

        //executam requestul
        requestSpecification.body(requestAccountBody);
        Response response = requestSpecification.post("Account/v1/User/");

        //validam responsul
        System.out.println(response.getStatusLine());
        Assert.assertTrue(response.getStatusLine().contains("201"));
        Assert.assertTrue(response.getStatusLine().contains("Created"));


        ResponseAccountSucces responseAccountSucces = response.getBody().as(ResponseAccountSucces.class);
        userID = responseAccountSucces.getUserId();

        Assert.assertTrue(responseAccountSucces.getUsername().equals(requestAccountBody.getUserName()));
        System.out.println(responseAccountSucces.getUserId());
    }

    public void generateToken() {
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com/");
        requestSpecification.contentType("application/json");

        requestSpecification.body(requestAccountBody);
        Response response = requestSpecification.post("Account/v1/GenerateToken/");

        System.out.println(response.getStatusLine());
        Assert.assertTrue(response.getStatusLine().contains("200"));
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseTokenSucces responseTokenSucces = response.getBody().as(ResponseTokenSucces.class);
        token = responseTokenSucces.getToken();

        Assert.assertEquals(responseTokenSucces.getStatus(), "Success");
        Assert.assertEquals(responseTokenSucces.getResult(), "User authorized successfully.");
    }

    public void checkAccPresence() {
        //configuram Clientul
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com");
        requestSpecification.contentType("application/json");

        //ne autorizam pe baza la Token
        requestSpecification.header("Authorization", "Bearer " + token);

        //executam requestul
        Response response = requestSpecification.get("/Account/v1/User/" + userID);

        //validam response-ul
        if(response.getStatusLine().contains("200")) {
            Assert.assertTrue(response.getStatusLine().contains("200"));
            Assert.assertTrue(response.getStatusLine().contains("OK"));
            System.out.println(response.getStatusCode());
        } else {
            Assert.assertTrue(response.getStatusLine().contains("401"));
            Assert.assertTrue(response.getStatusLine().contains("Unauthorized"));
            System.out.println(response.getStatusCode());
        }
    }

        public void deleteUser(){
        //configuram Clientul
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://demoqa.com/");
        requestSpecification.contentType("application/json");

        //ne autorizam pe baza la Token
        requestSpecification.header("Authorization","Bearer "+ token);

        //executam requestul
        Response response = requestSpecification.delete("Account/v1/User/" + userID);
        System.out.println(response.getStatusLine());
    }


    }


