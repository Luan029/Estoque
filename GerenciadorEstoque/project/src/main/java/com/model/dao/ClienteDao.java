package com.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.entidades.Cliente;

public class ClienteDao {

    private Connection connection;

    public ClienteDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Cliente cliente) throws SQLException{
        String sql = "INSERT INTO cliente(nome, endereco, telefone) VALUES(?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getTelefone());
            System.out.println("Cliente cadastrado");

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro a tentar inserir cliente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void delete(String nome) throws SQLException {
        String sql = "DELETE FROM cliente WHERE nome = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar excluir o cliente pelo nome");
            ex.printStackTrace();
        }
    }

    public void update(String nome, String novoNome, String novoEndereco, String novoTelefone) throws SQLException {
        String sql = "UPDATE cliente SET nome=?, endereco=?, telefone=? WHERE nome=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEndereco);
            stmt.setString(3, novoTelefone);
            stmt.setString(4, nome);
        
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar atualizar o cliente");
            ex.printStackTrace();
        }
    }

    public List<Cliente> findAllClientes() throws SQLException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id_cliente = resultSet.getInt("id_cliente");
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                String telefone = resultSet.getString("telefone");

                Cliente cliente = new Cliente(nome, endereco, telefone);
                cliente.setId(id_cliente);

                clientes.add(cliente);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar clientes");
            ex.printStackTrace();
        }

        return clientes;
    }

    public List<Cliente> findClientesPorNome(String texto) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE nome LIKE ?";
        List<Cliente> clientes = new ArrayList<>();
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + texto + "%");
            ResultSet resultSet = stmt.executeQuery();
    
            while (resultSet.next()) {
                int id_cliente = resultSet.getInt("id_cliente");
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                String telefone = resultSet.getString("telefone");
    
                Cliente cliente = new Cliente(nome, endereco, telefone);
                cliente.setId(id_cliente);

                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar clientes por nome");
            ex.printStackTrace();
        }
    
        return clientes;
    }

    public int findClienteIdByNome(String nome) throws SQLException {
    String sql = "SELECT id_cliente FROM cliente WHERE nome = ?";
    int id_cliente = 0;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nome);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            id_cliente = resultSet.getInt("id_cliente");
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao buscar ID do cliente por nome");
        ex.printStackTrace();
    }

        return id_cliente;
    }
    
}
