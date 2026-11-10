package financeiro.f1;

import financeiro.RepositorioTransacoes;
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
}