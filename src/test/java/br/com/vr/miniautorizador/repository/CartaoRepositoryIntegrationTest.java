package br.com.vr.miniautorizador.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import br.com.vr.miniautorizador.model.entity.Cartao;

@SpringBootTest
@ActiveProfiles("test")
public class CartaoRepositoryIntegrationTest {

    @Autowired
    private CartaoRepository repository;

    @Test
    public void criarNovoCartao_Sucesso() {
        Cartao cartaoNovo = Cartao.builder().numeroCartao(1111L).senha("123").valor(new BigDecimal(500.0)).build();
        Cartao cartaoCriado = repository.save(cartaoNovo);
        assertTrue(cartaoCriado != null);
    }

    @Test()
    public void criarNovoCartao_ErroCartaoExistente() {

        Cartao cartaoNovo1 = Cartao.builder().numeroCartao(1234L).senha("123").valor(new BigDecimal(500.0)).build();
        repository.save(cartaoNovo1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            Cartao cartaoNovo2 = Cartao.builder().numeroCartao(1234L).senha("456").valor(new BigDecimal(500.0)).build();
            repository.save(cartaoNovo2);
        });
    }

}
