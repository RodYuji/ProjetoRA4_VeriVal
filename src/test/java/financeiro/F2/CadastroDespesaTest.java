package financeiro.F2;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CadastroDespesaTest {

    @Test
    void deveCadastrarDespesaValida() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        CadastroDespesa cadastro = new CadastroDespesa(repo);

        cadastro.cadastrar(150.0, LocalDate.of(2026, 11, 6), "Mercado");

        assertEquals(1, repo.listarTodas().size());
        assertEquals(TipoTransacao.DESPESA, repo.listarTodas().get(0).getTipo());
    }
    @Test
    void deveRejeitarDespesaComValorZero() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        CadastroDespesa cadastro = new CadastroDespesa(repo);

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> cadastro.cadastrar(0.0, LocalDate.of(2026, 11, 6), "Mercado"));

        assertEquals("Valor deve ser maior que zero", erro.getMessage());
        assertEquals(0, repo.listarTodas().size());
    }
}