package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.github.scribejava.core.model.Response;

import api.endpoints.UserEndpoints;
import api.payload.User;
import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponse;
public class UserTests {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData()
	{
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

	}
	
	@Test(priority = 1)
	public void testPostUser(){
		
	io.restassured.response.Response res = UserEndpoints.createUser(userPayload);
	res.then().log().all();
	Assert.assertEquals(res.getStatusCode(), 200);
		}
	
	@Test(priority = 2)
	public void testGetUserbyUsername() {
		
		io.restassured.response.Response res = UserEndpoints.readUser(this.userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3)
	public void testUpdateUserbyUsername(){
	
	//update data using payload
	userPayload.setFirstName(faker.name().firstName());
	userPayload.setLastName(faker.name().lastName());
	userPayload.setEmail(faker.internet().safeEmailAddress());
	
		
	io.restassured.response.Response res = UserEndpoints.updateUser(this.userPayload.getUsername(), userPayload);
	res.then().log().all();
	Assert.assertEquals(res.getStatusCode(), 200);
	
		
		io.restassured.response.Response resAfterUpdate = UserEndpoints.readUser(this.userPayload.getUsername());
		resAfterUpdate.then().log().all();
		Assert.assertEquals(resAfterUpdate.getStatusCode(), 200);
		
	}
	
	@Test(priority = 4)
	public void testDeleteUserbyUsername() {
		
		io.restassured.response.Response res = UserEndpoints.deleteUser(this.userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(), 200);
		
	}
	
}
