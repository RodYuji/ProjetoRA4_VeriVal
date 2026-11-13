package financeiro.F8;

import financeiro.modelo.RepositorioOrcamentos;
import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.YearMonth;
import static org.junit.jupiter.api.Assertions.*;

class VerificarLimiteTest {

    private static final YearMonth NOVEMBRO = YearMonth.of(2026, 11);

    private RepositorioTransacoes transacoes;
    private RepositorioOrcamentos orcamentos;
    private VerificarLimite verificador;

    @BeforeEach
    void preparar() {
        transacoes = new RepositorioTransacoes();
        orcamentos = new RepositorioOrcamentos();
        orcamentos.salvar(NOVEMBRO, 3000.0);
        verificador = new VerificarLimite(transacoes, orcamentos);
    }

    private void despesa(double valor, LocalDate data) {
        transacoes.adicionar(new Transacao(valor, data, "Teste", TipoTransacao.DESPESA));
    }

    @Test
    void deveIndicarLimiteExcedidoQuandoDespesasSuperamTeto() {
        despesa(3200.0, LocalDate.of(2026, 11, 10));

        assertTrue(verificador.excedeu(NOVEMBRO));
        assertEquals(200.0, verificador.excedente(NOVEMBRO), 0.001);
    }

    @Test
    void naoDeveExcederQuandoDespesasAbaixoDoTeto() {
        despesa(2000.0, LocalDate.of(2026, 11, 10));

        assertFalse(verificador.excedeu(NOVEMBRO));
    }

    @Test
    void naoDeveExcederQuandoDespesasIguaisAoTeto() {
        despesa(3000.0, LocalDate.of(2026, 11, 10));

        assertFalse(verificador.excedeu(NOVEMBRO));
    }

    @Test
    void deveSomarVariasDespesasDoMes() {
        despesa(1500.0, LocalDate.of(2026, 11, 2));
        despesa(1600.0, LocalDate.of(2026, 11, 20));

        assertTrue(verificador.excedeu(NOVEMBRO));
    }

    @Test
    void deveIgnorarDespesasDeOutrosMeses() {
        despesa(5000.0, LocalDate.of(2026, 10, 15));
        despesa(1000.0, LocalDate.of(2026, 11, 15));

        assertFalse(verificador.excedeu(NOVEMBRO));
    }

    @Test
    void deveIgnorarReceitas() {
        transacoes.adicionar(new Transacao(9000.0, LocalDate.of(2026, 11, 5), "Salário", TipoTransacao.RECEITA));

        assertFalse(verificador.excedeu(NOVEMBRO));
    }

    @Test
    void deveRejeitarVerificacaoDeMesSemTeto() {
        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> verificador.excedeu(YearMonth.of(2026, 12)));

        assertEquals("Não há teto de gastos definido para o mês", erro.getMessage());
    }
}