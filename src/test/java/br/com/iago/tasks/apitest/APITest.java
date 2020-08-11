package br.com.iago.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://192.168.10.13:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		 RestAssured.given()
		 .when()
		 	.get("/todo")
		 .then()
		 	.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"Teste de api\", \"dueDate\": \"2020-12-30\"}")
			.contentType(ContentType.JSON)
		 .when()
		 	.post("/todo")
		 .then()
		 	.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaComDadoInvalido() {
		RestAssured.given()
			.body("{\"task\": \"Teste de api\", \"dueDate\": \"2010-12-30\"}")
			.contentType(ContentType.JSON)
		 .when()
		 	.post("/todo")
		 .then()
		 	.statusCode(400)
		 	.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
