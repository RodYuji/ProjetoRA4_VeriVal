package financeiro.F3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorCategorias {

    private final List<String> categorias = new ArrayList<>();

    public void cadastrar(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }
        String nomeNormalizado = nome.trim();
        if (existe(nomeNormalizado)) {
            throw new IllegalArgumentException("Categoria já cadastrada");
        }
        categorias.add(nomeNormalizado);
    }
    public boolean existe(String nome) {
        if (nome == null) return false;
        return categorias.stream().anyMatch(c -> c.equalsIgnoreCase(nome.trim()));
    }

    public List<String> listarTodas() {
        return Collections.unmodifiableList(categorias);
    }
}
