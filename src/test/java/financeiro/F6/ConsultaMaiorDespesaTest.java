package financeiro.F6;

import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class ConsultaMaiorDespesaTest {

    @Test
    void deveLancarExcecaoQuandoNaoHaDespesas() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        ConsultaMaiorDespesa consulta = new ConsultaMaiorDespesa(repo);

        assertThrows(NoSuchElementException.class, consulta::consultar);
    }

    @Test
    void deveRetornarUnicaDespesaCadastrada() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        new CadastroDespesa(repo).cadastrar(120.0, LocalDate.of(2026, 11, 7), "Amazon");
        ConsultaMaiorDespesa consulta = new ConsultaMaiorDespesa(repo);

        assertEquals(120.0, consulta.consultar().getValor(), 0.001);
    }

    @Test
    void deveRetornarMaiorDespesaEntreVarias() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        new CadastroDespesa(repo).cadastrar(150.0, LocalDate.of(2026, 11, 7), "Mercado");
        new CadastroDespesa(repo).cadastrar(800.0, LocalDate.of(2026, 11, 8), "Aluguel");
        new CadastroDespesa(repo).cadastrar(50.0,  LocalDate.of(2026, 11, 9), "Farmácia");
        ConsultaMaiorDespesa consulta = new ConsultaMaiorDespesa(repo);

        assertEquals(800.0, consulta.consultar().getValor(), 0.001);
    }
}