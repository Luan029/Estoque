package com.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.model.dao.ConexaoDao;
import com.model.dao.PedidoDao;
import com.model.dao.ProdutoDao;
import com.model.entidades.Pedido;

public class PedidoController {
    private static Connection connection = ConexaoDao.conectar();
    private static Pedido pedido;
    private static PedidoDao pedidoDao = new PedidoDao(connection);
    private static ProdutoDao produtoDao = new ProdutoDao(connection);
    private static Scanner scanner = new Scanner(System.in);

    public static void cadastroPedido() throws SQLException{
        
        try {
            System.out.println("-----------------------");
            System.out.println("* Cadastro de pedido*");
            System.out.println("-----------------------");
                
            System.out.println("Informe nome da cliente");
            String nomeCliente = scanner.nextLine();

            System.out.println("Informe codigo do produto");
            int id_produto = scanner.nextInt();

            System.out.println("Informe a quantidade");
            int quantidade = scanner.nextInt();

            int quantidadeEstoque =  produtoDao.findQuantidadeById(id_produto);
            int diferencaQuantidade = 0;

            if(quantidadeEstoque > 0){
                if(quantidadeEstoque >= quantidade){
                    int novaQuantidade = quantidadeEstoque - quantidade;
                    produtoDao.atualizaEstoque(id_produto, novaQuantidade);
                    System.out.println("Produto em estoque não é nescessario realizar pedido");

                }else{
                    diferencaQuantidade = quantidade - quantidadeEstoque;
                    produtoDao.atualizaEstoque(id_produto, 0);

                    int id_cliente = buscarIdClientePorNome(nomeCliente);

                    pedido = new Pedido(id_produto, diferencaQuantidade, id_cliente, "Cadastrado", quantidade);

                    // pedido.setRetiradoDoEstoque(true);

                    pedidoDao.insert(pedido);
                }

            }else{
                int id_cliente = buscarIdClientePorNome(nomeCliente);

                pedido = new Pedido(id_produto, quantidade, id_cliente, "Cadastrado", quantidade);

                pedidoDao.insert(pedido);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void excluirPedido() throws SQLException {
        System.out.println("Informe o numero do pedido que deseja excluir:");
        int numero = scanner.nextInt();
    
        pedidoDao.delete(numero);
    
        System.out.println("Produto excluído com sucesso!");
    }

    public static void atualizarPedido() throws SQLException {

        try {
            System.out.println("Informe o id do pedido que deseja atualizar:");
            int id_pedido = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Informe o novo nome do cliente:");
            String novoNomeCliente = scanner.nextLine();
    
            System.out.println("Informe o novo produto pelo id:");
            int novoId_produto = scanner.nextInt();
    
            System.out.println("Informe a nova quantidade do produto:");
            int novaQuantidade = scanner.nextInt();
            scanner.nextLine(); 
    
            
            int idNovaCliente = buscarIdClientePorNome(novoNomeCliente);
    
            pedidoDao.update(id_pedido, novoId_produto, novaQuantidade, idNovaCliente);
    
            System.out.println("Produto atualizado com sucesso!");
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarPedidos() throws SQLException {
        try {
            List<Pedido> pedidos = pedidoDao.listarTodos();
    
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado.");
            } else {
                System.out.println("Lista de Pedidos:");
                for (Pedido pedido : pedidos) {
                    System.out.println("ID Pedido: " + pedido.getNumero());
                    System.out.println("ID Cliente: " + pedido.getId_cliente());
                    System.out.println("ID Produto: " + pedido.getId_produto());
                    System.out.println("Quantidade: " + pedido.getQuantidade());
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarPedidosPorCliente() throws SQLException {
        try {
            System.out.println("Informe nome da cliente");
            String nomeCliente = scanner.nextLine();

            List<Pedido> pedidos = pedidoDao.listarPedidosPorCliente(nomeCliente);
    
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado para o cliente " + nomeCliente);
            } else {
                System.out.println("Pedidos para o cliente " + nomeCliente + ":");
                for (Pedido pedido : pedidos) {
                    System.out.println("\n");
                    System.out.println("ID Pedido: " + pedido.getNumero());
                    System.out.println("ID Cliente: " + pedido.getId_cliente());
                    System.out.println("ID Produto: " + pedido.getId_produto());
                    System.out.println("Quantidade: " + pedido.getQuantidade());
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarPedidosPorProduto() throws SQLException {
        try {

            System.out.println("Informe nome do produto");
            String nomeProduto = scanner.nextLine();

            List<Pedido> pedidos = pedidoDao.listarPedidosPorProduto(nomeProduto);
    
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado para o produto " + nomeProduto);
            } else {
                System.out.println("Pedidos para o produto " + nomeProduto + ":");
                for (Pedido pedido : pedidos) {
                    System.out.println("\n");
                    System.out.println("ID Pedido: " + pedido.getNumero());
                    System.out.println("ID Cliente: " + pedido.getId_cliente());
                    System.out.println("ID Produto: " + pedido.getId_produto());
                    System.out.println("Quantidade: " + pedido.getQuantidade());
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void listarPedidosPorMarca() throws SQLException {
        try {
            System.out.println("Informe nome da marca");
            String nomeMarca = scanner.nextLine();

            List<Pedido> pedidos = pedidoDao.listarPedidosPorMarca(nomeMarca);
    
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido encontrado para a marca " + nomeMarca);
            } else {
                System.out.println("Pedidos para a marca " + nomeMarca + ":");
                for (Pedido pedido : pedidos) {
                    System.out.println("\n");
                    System.out.println("ID Pedido: " + pedido.getNumero());
                    System.out.println("ID Cliente: " + pedido.getId_cliente());
                    System.out.println("ID Produto: " + pedido.getId_produto());
                    System.out.println("Quantidade: " + pedido.getQuantidade());
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    public static int buscarIdClientePorNome(String nomeCliente) throws SQLException {
        int id_cliente = pedidoDao.findClienteIdByNome(nomeCliente);
        return id_cliente;
    }

    public static int buscarIdProdutoPorNome(String nomeProduto) throws SQLException {
        int id_produto = pedidoDao.findClienteIdByNome(nomeProduto);
        return id_produto;
    }
}
