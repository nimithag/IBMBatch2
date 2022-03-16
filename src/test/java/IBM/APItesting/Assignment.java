package IBM.APItesting;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import org.testng.ITestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Assignment {
	@Test
	public void createUser(ITestContext userName,ITestContext pwd) throws JsonProcessingException {
			
			PojoClass Obj=new PojoClass();
			
			Obj.setUsername("Nimitha");
			Obj.setFirstname("Nimi");
			Obj.setLastname("Garlapati");
			Obj.setPassword("Welcome2ibm");
			Obj.setEmail("garlapnimi@gmail.com");
			Obj.setUserstatus("0");
			
			String username=Obj.getUsername();
			String password=Obj.getPassword();
			
			ObjectMapper objectMapper=new ObjectMapper();
			String response=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Obj);
		
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			RestAssured.given().contentType(ContentType.JSON).body(response)
			.when().post("/user").then().statusCode(200).log().all();
			
			userName.setAttribute("USERNAME", username);
			
			pwd.setAttribute("PASSWORD", password);		
		}
		
		
		@Test(dependsOnMethods="createUser")
		public void updateUser(ITestContext userName) throws JsonProcessingException 
		{
			String Username=userName.getAttribute("USERNAME").toString();
			
			PojoClass Obj=new PojoClass();
			
			Obj.setUsername("Nimitha");
			Obj.setFirstname("Nimi");
			Obj.setLastname("Garlapati");
			Obj.setPassword("Welcome2ibm");
			Obj.setEmail("garlapnimi@gmail.com");
			Obj.setUserstatus("0");
			
			
			ObjectMapper objectMapper=new ObjectMapper();
			String response=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(Obj);
			
			System.out.println(response);
			
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			RestAssured.given().contentType(ContentType.JSON)
			.body(response).when().put("/user/"+Username).then().statusCode(200).log().all();

		}

		@Test(dependsOnMethods="createUser")
		public void login(ITestContext userName,ITestContext pwd) throws JsonProcessingException 
		{
			String username=userName.getAttribute("USERNAME").toString();
			String password=pwd.getAttribute("PASSWORD").toString();
			
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			RestAssured.given().get("/user/login?username="+username+"&password="+password).then().statusCode(200).log().all();

		}
		@Test(dependsOnMethods="login")
		public void logout(ITestContext userName) throws JsonProcessingException 
		{
			String username=userName.getAttribute("USERNAME").toString();
			
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			RestAssured.given().get("/user/logout?username="+username).then().statusCode(200).log().all();

		}
		
		@Test(dependsOnMethods="login")
		public void deleteUser(ITestContext userName) throws JsonProcessingException 
		{
			String username=userName.getAttribute("USERNAME").toString();
			RestAssured.baseURI="https://petstore.swagger.io/v2";
			RestAssured.given().delete("/user/" +username).then().statusCode(200).log().all();

		}

		
	}



