package com.fabiolucas.faculdade.data.model;

import java.util.ArrayList;

public class DadosGerais {

    ArrayList todosOsDados;

    public DadosGerais(ArrayList todosOsDados) {
        this.todosOsDados = todosOsDados;
    }

    public DadosGerais() {
    }

    public ArrayList getTodosOsDados() {
        return todosOsDados;
    }

    public void setTodosOsDados(ArrayList todosOsDados) {
        this.todosOsDados = todosOsDados;
    }
}
