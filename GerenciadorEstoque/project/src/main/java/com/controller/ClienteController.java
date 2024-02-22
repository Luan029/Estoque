package com.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

import com.model.dao.ConexaoDao;
import com.model.dao.ClienteDao;
import com.model.entidades.Cliente;

public class ClienteController {
    private static Connection connection = ConexaoDao.conectar();
    private static Cliente cliente;
    private static ClienteDao clienteDao = new ClienteDao(connection);
    private static Scanner scanner = new Scanner(System.in);

    public static Cliente cadastrarCliente() throws SQLException{

        try{
        System.out.println("-----------------------");
        System.out.println("* Cadastro de cliente *");
        System.out.println("-----------------------");    

        System.out.println("Informe o nome do cliente:");
        String nome = scanner.nextLine();

        System.out.println("Informe o endereco do cliente:");
        String endereco = scanner.nextLine();

        System.out.println("Informe o telefone do cliente:");
        String telefone = scanner.nextLine();

        if (!telefone.matches("\\d+")) {
            System.out.println("Numero de telefone deve conter apenas dígitos.");
            return null;
        }

        if (telefone.length() != 11) {
            System.out.println("Número de telefone deve conter 11 dígitos, sendo 2 digitos para o DDD do estado e 9 digitos para o número do telefone.");
            return null;
        }

        cliente = new Cliente(nome, endereco, telefone);

        clienteDao.insert(cliente);

        return cliente;

        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void atualizarCliente() throws SQLException {
        try {
            System.out.println("---------------------");
            System.out.println("* Atualizar cliente *");
            System.out.println("---------------------"); 

            System.out.println("Informe o nome do cliente que deseja atualizar:");
            String nome = scanner.nextLine();
    
            System.out.println("Informe o novo nome do cliente:");
            String novoNome = scanner.nextLine();
    
            System.out.println("Informe o novo endereco do cliente:");
            String novoEndereco = scanner.nextLine();
    
            System.out.println("Informe o novo telefone do cliente:");
            String novoTelefone = scanner.nextLine();

            if (!novoTelefone.matches("\\d+")) {
            System.out.println("Numero de telefone deve conter apenas dígitos.");
            return;
            }

            if (novoTelefone.length() != 11) {
            System.out.println("Número de telefone deve conter 11 dígitos, sendo 2 digitos para o DDD do estado e 9 digitos para o número do telefone.");
            return;
            }

            int idNovo = buscarIdPorNome(nome);
    
            if (idNovo == 0) {
                System.out.println("O novo cliente informado não existe.");
                return;
            }

    
            clienteDao.update(nome, novoNome, novoEndereco, novoTelefone);
    
            System.out.println("Cliente atualizado com sucesso!");
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarClientes() throws SQLException {
        System.out.println("---------------------");
        System.out.println("* Listar clientes *");
        System.out.println("---------------------");
        try {
            List<Cliente> clientes = clienteDao.findAllClientes();

            if (!clientes.isEmpty()) {
                System.out.println("Lista de Clientes:");

                for (Cliente cliente : clientes) {
                    System.out.println("Id: " + cliente.getId());
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.println("Endereco: " + cliente.getEndereco());
                    System.out.println("Telefone: " + cliente.getTelefone());
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum cliente encontrado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void excluirCliente() throws SQLException {
        System.out.println("---------------------");
        System.out.println("* Excluir cliente *");
        System.out.println("---------------------"); 
        System.out.println("Informe o nome do cliente que deseja excluir:");
        String nome = scanner.nextLine();
    
        try {
            clienteDao.delete(nome);
            System.out.println("Cliente excluído com sucesso!");
        } catch (PSQLException ex) {
            if (ex.getMessage().contains("violates foreign key constraint")) {
                System.out.println("O cliente possui pedidos, logo não pode ser excluído.");
            } else {
                ex.printStackTrace();
            }
        }
        
    }

    public static void listarClientesPorNome() throws SQLException {
        try {
            System.out.println("Informe o texto a ser pesquisado no nome dos clientes:");
            String texto = scanner.nextLine();
    
            List<Cliente> clientes = clienteDao.findClientesPorNome(texto);
    
            if (!clientes.isEmpty()) {
                System.out.println("Clientes com nomes contendo '" + texto + "':");
    
                for (Cliente cliente : clientes) {
                    System.out.println("Id: " + cliente.getId());
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.println("Endereco: " + cliente.getEndereco());
                    System.out.println("Telefone: " + cliente.getTelefone());
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum cliente encontrado com o texto '" + texto + "' no nome.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   public static int buscarIdPorNome(String nome) throws SQLException {
        int id = clienteDao.findClienteIdByNome(nome);
        return id;
    }
    
}
