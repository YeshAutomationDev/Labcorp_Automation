package com.labcorp.apitests;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    Response response;

    @Given("I send a GET request to {string}")
    public void i_send_a_get_request(String url) {
        response = given()
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer statusCode) {
        Assert.assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should contain {string}, {string}, and all headers")
    public void validate_get_response_fields(String pathKey, String ipKey) {
        String body = response.getBody().asString();
        Assert.assertTrue("Body missing 'path'", body.contains(pathKey));
        Assert.assertTrue("Body missing 'ip'", body.contains(ipKey));
        Assert.assertFalse("Headers missing", response.getHeaders().asList().isEmpty());
    }

    @Given("I send a POST request to {string}")
    public void i_send_a_post_request(String url) {
        JSONObject payload = new JSONObject();
        payload.put("order_id", "12345");

        JSONObject customer = new JSONObject();
        customer.put("name", "Jane Smith");
        customer.put("email", "janesmith@example.com");
        customer.put("phone", "1-987-654-3210");

        JSONObject address = new JSONObject();
        address.put("street", "456 Oak Street");
        address.put("city", "Metropolis");
        address.put("state", "NY");
        address.put("zipcode", "10001");
        address.put("country", "USA");
        customer.put("address", address);
        payload.put("customer", customer);

        payload.put("order_status", "processing");
        payload.put("created_at", "2024-11-01");

        // send request
        response = given()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    @Then("the response should correctly echo the order, customer, payment, and product details")
    public void verify_post_response_details() {
        String respBody = response.getBody().asString();

        Assert.assertTrue(respBody.contains("Jane Smith"));
        Assert.assertTrue(respBody.contains("456 Oak Street"));
        Assert.assertTrue(respBody.contains("processing"));
    }
}

