package apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefasComSucesso(){
        RestAssured.given()
                .body("{\n" +
                        "    \"task\":\"Teste via Api\",\n" +
                        "    \"dueDate\":\"2024-01-07\"    \n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201);

    }

    @Test
    public void naoDeveAdicionarTarefasComSucesso(){
        RestAssured.given()
                .body("{\n" +
                        "    \"task\":\"Teste via Api\",\n" +
                        "    \"dueDate\":\"2005-12-07\"    \n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));

    }

}
