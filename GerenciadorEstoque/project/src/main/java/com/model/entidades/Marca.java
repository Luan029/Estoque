package com.model.entidades;

public class Marca {
    private String nome;
    private int diaPedido;
    private int codMarca;

    public Marca(String nome, int dataEnvio, int codMarca) {
        this.nome = nome;
        this.diaPedido = dataEnvio;
        this.codMarca = codMarca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDiaPedido() {
        return diaPedido;
    }

    public void setDiaPedido(int dataPedido) {
        this.diaPedido = dataPedido;
    }

    public int getCodMarca() {
        return codMarca;
    }

    public void setCodMarca(int codMarca) {
        this.codMarca = codMarca;
    }
    
}
