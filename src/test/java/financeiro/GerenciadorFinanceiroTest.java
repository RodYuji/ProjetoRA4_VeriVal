package financeiro;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GerenciadorFinanceiroTest {

    @Test
    void deveCadastrarReceitaValida() {
        GerenciadorFinanceiro g = new GerenciadorFinanceiro();
        g.cadastrarReceita(3000.0, LocalDate.of(2026, 11, 5), "Salário");
        assertEquals(1, g.listarTransacoes().size());
    }
}