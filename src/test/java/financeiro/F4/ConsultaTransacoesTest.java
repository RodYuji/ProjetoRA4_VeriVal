package financeiro.F4;

import financeiro.modelo.RepositorioTransacoes;
import financeiro.modelo.TipoTransacao;
import financeiro.modelo.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConsultaTransacoesTest {

    private RepositorioTransacoes repo;
    private ConsultaTransacoes consulta;

    @BeforeEach
    void setUp() {
        repo = new RepositorioTransacoes();
        consulta = new ConsultaTransacoes(repo);
    }

    @Test
    void deveListarTransacoesDoMes() {
        repo.adicionar(new Transacao(100.0, LocalDate.of(2026, 11, 5), "Almoço", TipoTransacao.DESPESA));
        repo.adicionar(new Transacao(200.0, LocalDate.of(2026, 10, 5), "Cinema", TipoTransacao.DESPESA));

        List<Transacao> resultado = consulta.listarPorMes(11);

        assertEquals(1, resultado.size());
    }

    @Test
    void deveRetornarMensagemdeErroQuandoNaoHouverTransacoesNoMes() {
        IllegalArgumentException erro = assertThrows(
                IllegalArgumentException.class,
                () -> consulta.listarPorMes(13)
        );

        assertEquals("Mês inválido", erro.getMessage());
    }
}