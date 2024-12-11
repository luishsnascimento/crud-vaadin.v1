package com.example.application.components;

import com.example.application.models.Pessoa;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class PessoaGrid extends VerticalLayout {
    private final Grid<Pessoa> grid = new Grid<>(Pessoa.class);

    public PessoaGrid() {
        grid.setColumns("nome", "sobrenome", "dataNascimento", "email", "nomePai", "nomeMae");
        setWidthFull(); // Garante que o grid preencha a largura disponível
        setPadding(false);
        add(grid);
    }

    public void atualizarLista(List<Pessoa> pessoas) {
        grid.setItems(pessoas);
    }

    public void adicionarBotaoEditar(EditAction action) {
        grid.addComponentColumn(pessoa -> {
            Button editarButton = new Button("Editar");
            editarButton.addClickListener(e -> action.execute(pessoa));
            return editarButton;
        }).setHeader("Editar");
    }

    public void adicionarBotaoDeletar(DeleteAction action) {
        grid.addComponentColumn(pessoa -> {
            Button deletarButton = new Button("Deletar");
            deletarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deletarButton.addClickListener(e -> action.execute(pessoa));
            return deletarButton;
        }).setHeader("Deletar");
    }

    @FunctionalInterface
    public interface EditAction {
        void execute(Pessoa pessoa);
    }

    @FunctionalInterface
    public interface DeleteAction {
        void execute(Pessoa pessoa);
    }
}

