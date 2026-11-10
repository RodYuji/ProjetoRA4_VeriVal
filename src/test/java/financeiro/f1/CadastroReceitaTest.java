package financeiro.f1;

import financeiro.modelo.RepositorioTransacoes;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CadastroReceitaTest {

    @Test
    void deveCadastrarReceitaValida() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        CadastroReceita cadastro = new CadastroReceita(repo);

        cadastro.cadastrar(3000.0, LocalDate.of(2026, 11, 5), "Salário");

        assertEquals(1, repo.listarTodas().size());
    }
    @Test
    void deveRejeitarReceitaComValorZero() {
        RepositorioTransacoes repo = new RepositorioTransacoes();
        CadastroReceita cadastro = new CadastroReceita(repo);

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> cadastro.cadastrar(0.0, LocalDate.of(2026, 11, 5), "Salário"));

        assertEquals("Valor deve ser maior que zero", erro.getMessage());
        assertEquals(0, repo.listarTodas().size());
    }
}