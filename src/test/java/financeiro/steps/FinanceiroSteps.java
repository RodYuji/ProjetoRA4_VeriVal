package financeiro.steps;

import financeiro.F1.CadastroReceita;
import financeiro.F2.CadastroDespesa;
import financeiro.modelo.RepositorioTransacoes;
import io.cucumber.java.pt.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class FinanceiroSteps {

    private final RepositorioTransacoes repo = new RepositorioTransacoes();
    private Exception erro;

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

    @Dado("que eu cadastro uma despesa de {double} em {string} com descrição {string}")
    public void queEuCadastroUmaDespesa(Double valor, String data, String descricao) {
        new CadastroDespesa(repo).cadastrar(valor, LocalDate.parse(data), descricao);
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

    private final financeiro.F3.GerenciadorCategorias gerenciadorCategorias = new financeiro.F3.GerenciadorCategorias();
    private Exception erroCategoria;

    @Dado("que a categoria {string} já foi cadastrada")
    public void queACategoriaJaFoiCadastrada(String nome) {
        gerenciadorCategorias.cadastrar(nome);
    }

    @Quando("eu cadastro a categoria {string}")
    public void euCadastroACategoria(String nome) {
        try {
            gerenciadorCategorias.cadastrar(nome);
        } catch (IllegalArgumentException e) {
            erroCategoria = e;
        }
    }

    @Então("a categoria {string} deve estar cadastrada")
    public void aCategoriaDeveEstarCadastrada(String nome) {
        assertTrue(gerenciadorCategorias.existe(nome));
    }

    @Então("o sistema deve conter {int} categorias cadastradas")
    public void oSistemaDeveConterCategoriasCadastradas(Integer quantidade) {
        assertEquals(quantidade, gerenciadorCategorias.listarTodas().size());
    }

    @Então("o sistema deve rejeitar a categoria com a mensagem {string}")
    public void oSistemaDeveRejeitarACategoriaComAMensagem(String mensagemEsperada) {
        assertNotNull(erroCategoria, "Esperava-se um erro, mas a operação foi aceita");
        assertEquals(mensagemEsperada, erroCategoria.getMessage());
    }

    private final financeiro.F4.ConsultaTransacoes consulta = new financeiro.F4.ConsultaTransacoes(repo);
    private java.util.List<financeiro.modelo.Transacao> listaMes;
    private Exception erroConsulta;

    @Quando("eu listo as transações do mês {int}")
    public void euListoAsTransacoesDoMes(Integer mes) {
        try {
            listaMes = consulta.listarPorMes(mes);
        } catch (IllegalArgumentException e) {
            erroConsulta = e;
        }
    }

    @Então("devem ser encontradas {int} transações")
    public void devemSerEncontradasTransacoes(Integer qtd) {
        assertEquals(qtd, listaMes.size());
    }

    @Então("o sistema deve rejeitar a consulta com a mensagem {string}")
    public void oSistemaDeveRejeitarAConsultaComAMensagem(String msgEsperada) {
        assertNotNull(erroConsulta, "Esperava-se um erro, mas nenhum foi lançado");
        assertEquals(msgEsperada, erroConsulta.getMessage());
    }
}