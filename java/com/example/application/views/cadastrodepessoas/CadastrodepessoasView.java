package com.example.application.views.cadastrodepessoas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.example.application.components.PessoaGrid;
import com.example.application.models.Pessoa;
import com.example.application.services.PessoaService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro de pessoas")
@Route("person-form")
@Menu(order = 1, icon = LineAwesomeIconUrl.USER)
public class CadastrodepessoasView extends Composite<VerticalLayout> {

    private final PessoaService pessoaService;
    private final PessoaGrid pessoaGrid = new PessoaGrid();
    private Pessoa pessoaEditando = null;

    @Autowired
    public CadastrodepessoasView(PessoaService pessoaService) {
        this.pessoaService = pessoaService;

        H3 titulo = new H3("Dados pessoais");
        FormLayout formLayout = new FormLayout();
        TextField nomeField = new TextField("Nome");
        TextField sobrenomeField = new TextField("Sobrenome");
        DatePicker dataNascimentoField = new DatePicker("Data de nascimento");
        EmailField emailField = new EmailField("Email");
        TextField nomePaiField = new TextField("Nome do pai");
        TextField nomeMaeField = new TextField("Nome da mãe");
        Button salvarButton = new Button("Salvar");
        Button cancelarButton = new Button("Cancelar");

        formLayout.add(nomeField, sobrenomeField, dataNascimentoField, emailField, nomePaiField, nomeMaeField);

        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Confirmação");
        confirmDialog.add("Atenção! Todos os dados serão perdidos, deseja mesmo deletar o cadastro?");
        Button confirmarButton = new Button("Sim", e -> {
            if (pessoaEditando != null) {
                pessoaService.deletePessoa(pessoaEditando.getId());
                atualizarGrid();
                Notification.show("Pessoa removida com sucesso!");
                confirmDialog.close();
            }
        });
        Button cancelarDialogButton = new Button("Não", e -> confirmDialog.close());
        confirmarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelarDialogButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        HorizontalLayout dialogButtons = new HorizontalLayout(confirmarButton, cancelarDialogButton);
        confirmDialog.getFooter().add(dialogButtons);

        pessoaGrid.adicionarBotaoEditar(pessoa -> {
            pessoaEditando = pessoa;
            nomeField.setValue(pessoa.getNome());
            sobrenomeField.setValue(pessoa.getSobrenome());
            dataNascimentoField.setValue(pessoa.getDataNascimento());
            emailField.setValue(pessoa.getEmail());
            nomePaiField.setValue(pessoa.getNomePai());
            nomeMaeField.setValue(pessoa.getNomeMae());
        });

        pessoaGrid.adicionarBotaoDeletar(pessoa -> {
            pessoaEditando = pessoa;
            confirmDialog.open(); 
        });

        atualizarGrid();

        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickListener(e -> {
            String nome = nomeField.getValue().trim();
            String sobrenome = sobrenomeField.getValue().trim();
            LocalDate dataNascimento = dataNascimentoField.getValue();
            String email = emailField.getValue().trim();
            String nomePai = nomePaiField.getValue().trim();
            String nomeMae = nomeMaeField.getValue().trim();

            if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || dataNascimento == null) {
                Notification.show("Preencha todos os campos obrigatórios!");
                return;
            }

            if (pessoaEditando == null) {
                Pessoa novaPessoa = new Pessoa(nome, sobrenome, dataNascimento, email, nomePai, nomeMae);
                pessoaService.savePessoa(novaPessoa);
                Notification.show("Pessoa salva com sucesso!");
            } else {
                pessoaEditando.setNome(nome);
                pessoaEditando.setSobrenome(sobrenome);
                pessoaEditando.setDataNascimento(dataNascimento);
                pessoaEditando.setEmail(email);
                pessoaEditando.setNomePai(nomePai);
                pessoaEditando.setNomeMae(nomeMae);
                pessoaService.savePessoa(pessoaEditando);
                Notification.show("Pessoa atualizada com sucesso!");
                pessoaEditando = null;
            }

            atualizarGrid();

            nomeField.clear();
            sobrenomeField.clear();
            dataNascimentoField.clear();
            emailField.clear();
            nomePaiField.clear();
            nomeMaeField.clear();
        });

        cancelarButton.addClickListener(e -> {
            nomeField.clear();
            sobrenomeField.clear();
            dataNascimentoField.clear();
            emailField.clear();
            nomePaiField.clear();
            nomeMaeField.clear();
            pessoaEditando = null;
        });

        HorizontalLayout botoes = new HorizontalLayout(salvarButton, cancelarButton);

        VerticalLayout layoutPrincipal = getContent();
        layoutPrincipal.add(titulo, formLayout, botoes, pessoaGrid);
    }

    private void atualizarGrid() {
        List<Pessoa> pessoas = pessoaService.getAllPessoas();
        pessoaGrid.atualizarLista(pessoas);
    }
}
