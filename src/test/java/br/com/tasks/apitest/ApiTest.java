package br.com.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup(){
		RestAssured.baseURI = "http://localhost:8080/tasks-backend";
	}
	
	@Test
	public void shouldReturnTasks() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void shouldAddTasks() {
		RestAssured.given()
			.body("{\"task\": \"teste\",\"dueDate\": \"2020-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}
	
	@Test
	public void shouldAddTasksInvalid() {
		RestAssured.given()
			.body("{\"task\": \"teste\",\"dueDate\": \"2010-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}

