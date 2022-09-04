package br.com.vr.miniautorizador.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.vr.miniautorizador.model.dto.CartaoNovoDTO;
import br.com.vr.miniautorizador.model.dto.TransacaoNovaDTO;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/db/insertDataTest.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "/db/deleteDataTest.sql",executionPhase = AFTER_TEST_METHOD)
public class AutorizadorControllerIntegrationTest {

        @LocalServerPort
        private int port;
      
        @Test
        void testConsultarSaldoCartao_Sucesso() {
                given()
                        .basePath("mini-autorizador/cartoes/{numeroCartao}")
                        .accept(ContentType.JSON)
                        .port(port)
                        .pathParam("numeroCartao", "1111111111111111")
                .when()
                        .get()
                .then()
                        .statusCode(HttpStatus.OK.value())
                        .body(equalTo("500.00"));
        }

        @Test
        void testConsultarSaldoCartao_ErroCartaoInexistente() {
                given()
                        .basePath("mini-autorizador/cartoes/{numeroCartao}")
                        .accept(ContentType.JSON)
                        .port(port)
                        .pathParam("numeroCartao", "0")
                .when()
                        .get()
                .then()
                        .statusCode(HttpStatus.NOT_FOUND.value());
        }

        @Test
        void testCriarCartao() {
                CartaoNovoDTO cartaoNovo = CartaoNovoDTO.builder().numeroCartao(123456789L).senha("123").build();
                given()
                        .basePath("mini-autorizador/cartoes")
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(cartaoNovo)
                        .log().all()
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void testCriarTransacao_Sucesso() {
                TransacaoNovaDTO transacaoNova = TransacaoNovaDTO.builder().numeroCartao(2222222222222222L).senhaCartao("456").valor(new BigDecimal(100.00)).build();
                given()
                        .basePath("mini-autorizador/transacoes")
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(transacaoNova)
                        .log().all()
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.CREATED.value())
                        .body(equalTo("OK"));
        }

        @Test
        void testCriarTransacao_ErroCartaoInexistente() {
                TransacaoNovaDTO transacaoNova = TransacaoNovaDTO.builder().numeroCartao(2L).senhaCartao("456").valor(new BigDecimal(100.00)).build();
                given()
                        .basePath("mini-autorizador/transacoes")
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(transacaoNova)
                        .log().all()
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }

        @Test
        void testCriarTransacao_ErroSaldoInsuficiente() {
                TransacaoNovaDTO transacaoNova = TransacaoNovaDTO.builder().numeroCartao(2222222222222222L).senhaCartao("456").valor(new BigDecimal(900.00)).build();
                given()
                        .basePath("mini-autorizador/transacoes")
                        .contentType(ContentType.JSON)
                        .port(port)
                        .body(transacaoNova)
                        .log().all()
                .when()
                        .post()
                .then()
                        .log().all()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        }
}
