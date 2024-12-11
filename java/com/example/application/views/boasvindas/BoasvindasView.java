package com.example.application.views.boasvindas;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Boas vindas!")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.HAND_PEACE_SOLID)
public class BoasvindasView extends Composite<VerticalLayout> {

    public BoasvindasView() {
        VerticalLayout layoutPrincipal = getContent();
        layoutPrincipal.setAlignItems(Alignment.CENTER);
        layoutPrincipal.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutPrincipal.setSizeFull();

        H1 titulo = new H1("Bem-vindo ao Sistema de Cadastro de Pessoas!");
        titulo.getStyle().set("color", "#007BFF");

        Span texto = new Span("Cadastrar pessoas aqui:");
        texto.getStyle().set("font-size", "20px");

        Button botaoCadastrar = new Button("Ir para Cadastro");
        botaoCadastrar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        botaoCadastrar.addClickListener(event -> UI.getCurrent().navigate("/person-form"));

        layoutPrincipal.add(titulo, texto, botaoCadastrar);
    }
}
