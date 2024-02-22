package com.model.entidades;
public class Pedido {
    private int id_produto;
    private int quantidade;
    private int id_cliente;
    private String status;
    private int numero;
    
    public Pedido(int id_produto, int quantidade, int id_cliente, String status, int numero) {
        this.id_produto = id_produto;
        this.quantidade = quantidade;
        this.id_cliente = id_cliente;
        this.status = status;
        this.numero = numero;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
