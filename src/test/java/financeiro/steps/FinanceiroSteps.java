package financeiro.steps;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import io.cucumber.java.pt.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import financeiro.F7.DefinirOrcamento;
import financeiro.modelo.RepositorioOrcamentos;
import java.time.YearMonth;

public class FinanceiroSteps {

    private final RepositorioTransacoes repo = new RepositorioTransacoes();
    private Exception erro;
    private final RepositorioOrcamentos repoOrcamentos = new RepositorioOrcamentos();

    @Quando("eu cadastro uma receita de {double} em {string} com descrição {string}")
    public void euCadastroUmaReceita(Double valor, String data, String descricao) {
        try {
            new CadastroReceita(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }
    @Quando("eu cadastro uma despesa de {double} em {string} com descrição {string}")
    public void euCadastroUmaDespesa(Double valor, String data, String descricao) {
        try {
            new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    @Então("devem existir {int} transações cadastradas")
    public void devemExistirTransacoes(Integer quantidade) {
        assertEquals(quantidade, repo.listarTodas().size());
    }

    @Então("o sistema deve rejeitar com a mensagem {string}")
    public void oSistemaDeveRejeitar(String mensagem) {
        assertNotNull(erro, "Era esperado um erro, mas nenhum foi lançado");
        assertEquals(mensagem, erro.getMessage());
    }

    // ===== F7 - Definir teto de gastos =====

    @Dado("que o teto do mês {string} é {double}")
    public void queOTetoDoMesE(String mes, Double teto) {
        new DefinirOrcamento(repoOrcamentos).definir(YearMonth.parse(mes), teto);
    }

    @Quando("eu defino o teto de {double} para o mês {string}")
    public void euDefinoOTeto(Double teto, String mes) {
        try {
            new DefinirOrcamento(repoOrcamentos).definir(YearMonth.parse(mes), teto);
        } catch (IllegalArgumentException e) {
            erro = e;
        }
    }

    @Então("o teto do mês {string} deve ser {double}")
    public void oTetoDoMesDeveSer(String mes, Double esperado) {
        assertEquals(esperado, repoOrcamentos.buscar(YearMonth.parse(mes)).orElseThrow());
    }
}