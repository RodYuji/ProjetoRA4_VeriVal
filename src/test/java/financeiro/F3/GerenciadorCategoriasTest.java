package financeiro.F3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GerenciadorCategoriasTest {

    private GerenciadorCategorias gerenciador;

    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorCategorias();
    }

    @Test
    void deveCadastrarCategoriaComSucesso() {
        gerenciador.cadastrar("Alimentação");
        assertTrue(gerenciador.existe("Alimentação"));
        assertEquals(1, gerenciador.listarTodas().size());
    }

    @Test
    void deveRejeitarCategoriaComNomeVazioOuNulo() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> gerenciador.cadastrar("   "));

        assertEquals("Nome da categoria não pode ser vazio", erro.getMessage());
    }

    @Test
    void deveRejeitarCategoriaDuplicada() {
        gerenciador.cadastrar("Transporte");

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> gerenciador.cadastrar("transporte"));

        assertEquals("Categoria já cadastrada", erro.getMessage());
    }
}