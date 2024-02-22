package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.controller.ClienteController;
import com.controller.MarcaController;
import com.controller.PedidoController;
import com.controller.ProdutoController;

public class App 
{
    public static void main( String[] args ) throws SQLException{

        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n");
            System.out.println("Menu:");
            System.out.println("1. Gerenciar Pedidos");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Marcas");
            System.out.println("4. Gerenciar Clientes");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Submenu para gerenciar pedidos
                    menuPedidos();
                    break;
                case 2:
                    // Submenu para gerenciar produtos
                    menuProdutos();
                    break;
                case 3:
                    // Submenu para gerenciar marcas
                    menuMarcas();
                    break;
                case 4:
                    // Submenu para gerenciar clientes
                    menuClientes();
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);

        scanner.close();
    }

    // Métodos de submenu para Pedidos
    private static void menuPedidos() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n");
            System.out.println("Menu de Pedidos:");
            System.out.println("1. Criar um novo pedido");
            System.out.println("2. Listar todos os pedidos");
            System.out.println("3. Atualizar um pedido");
            System.out.println("4. Excluir um pedido");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    PedidoController.cadastroPedido();
                    break;
                case 2:
                    PedidoController.listarPedidos();
                break;
                case 3:
                    PedidoController.atualizarPedido();;
                    break;
                case 4:
                    PedidoController.excluirPedido();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);
    }

    // Métodos de submenu para Produtos
    private static void menuProdutos() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n");
            System.out.println("Menu de Produtos:");
            System.out.println("1. Criar um novo produto");
            System.out.println("2. Listar todos os produtos");
            System.out.println("3. Atualizar um produto");
            System.out.println("4. Excluir um produto");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    ProdutoController.cadastrarProduto();
                    break;
                case 2:
                    ProdutoController.listarProdutos();;
                    break;
                case 3:
                    ProdutoController.atualizarProduto();;
                    break;
                case 4:
                    ProdutoController.excluirProduto();;
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);
    }

    // Métodos de submenu para Marcas
    private static void menuMarcas() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n");
            System.out.println("Menu de Marcas:");
            System.out.println("1. Criar uma nova marca");
            System.out.println("2. Listar todas as marcas");
            System.out.println("3. Atualizar uma marca");
            System.out.println("4. Excluir uma marca");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    MarcaController.cadastroMarca();
                    break;
                case 2:
                    MarcaController.findAllMarcas();
                    break;
                case 3:
                    MarcaController.atualizarMarca();
                    break;
                case 4:
                    MarcaController.excluirMarca();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);
    }

    // Métodos de submenu para Clientes
    private static void menuClientes() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("\n");
            System.out.println("Menu de Clientes:");
            System.out.println("1. Criar um novo cliente");
            System.out.println("2. Listar todos os clientes");
            System.out.println("3. Atualizar um cliente");
            System.out.println("4. Excluir um cliente");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    ClienteController.cadastrarCliente();
                    break;
                case 2:
                    ClienteController.listarClientes();
                    break;
                case 3:
                    ClienteController.atualizarCliente();
                    break;
                case 4:
                    ClienteController.excluirCliente();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 5);

        do {
            System.out.println("\n");
            System.out.println("******************* MENU *******************"
            );
            System.out.println();
        }while(escolha != 5);

        try {
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/projetopoo",
            "postgres", "12345678");

            if (c != null){
                System.out.println("Banco de dados conectado");
            } else {
                System.out.println("Conexao falhou");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //MarcaController marca = new MarcaController();

        // MarcaController.cadastroMarca();
    }
}
