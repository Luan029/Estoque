package com.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.entidades.Pedido;

public class PedidoDao {

    private Connection connection;

    public PedidoDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void insert(Pedido pedido) throws SQLException{
        String sql = "INSERT INTO pedido(id_cliente, id_produto, quantidade, status) VALUES(?,?,?,?)";

        try (PreparedStatement stmt =  connection.prepareStatement(sql)) {
            
            stmt.setInt(1, pedido.getId_cliente());
            stmt.setInt(2, pedido.getId_produto());
            stmt.setInt(3, pedido.getQuantidade());
            stmt.setString(4, pedido.getStatus());

            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println("Erro ao tentar inserir pedido: ");
            ex.printStackTrace();
        }
    }

    public void delete(int numero) throws SQLException {
        String sql = "DELETE FROM pedido WHERE codigo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar excluir pedido por numero");
            ex.printStackTrace();
        }

    }

    public void update(int id_pedido, int novoId_produto, int novaQuantidade, int idNovaCliente) throws SQLException {
        String sql = "UPDATE pedido SET id_cliente=?, id_produto=?, quantidade=? WHERE codigo=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idNovaCliente);
            stmt.setInt(2, novoId_produto);
            stmt.setInt(3, novaQuantidade);
            stmt.setInt(4, id_pedido);
        
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar atualizar produto");
            ex.printStackTrace();
        }
    }

    public int findClienteIdByNome(String nomeCliente) throws SQLException {
        String sql = "SELECT id_cliente FROM cliente WHERE nome = ?";
        int id_cliente = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeCliente);
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

    public List<Pedido> listarTodos() throws SQLException {
    List<Pedido> pedidos = new ArrayList<>();

    String sql = "SELECT * FROM pedido";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int id_pedido = resultSet.getInt("codigo");
            int id_cliente = resultSet.getInt("id_cliente");
            int id_produto = resultSet.getInt("id_produto");
            int quantidade = resultSet.getInt("quantidade");

            Pedido pedido = new Pedido(id_produto, quantidade, id_cliente, "Cadastrado", id_pedido);

            pedidos.add(pedido);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao listar pedidos");
        ex.printStackTrace();
    }

    return pedidos;
}

public List<Pedido> listarPedidosPorCliente(String nomeCliente) throws SQLException {
    List<Pedido> pedidos = new ArrayList<>();

    String sql = "SELECT * FROM pedido p JOIN cliente c ON p.id_cliente = c.id_cliente WHERE c.nome = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nomeCliente);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int id_pedido = resultSet.getInt("codigo");
            int id_cliente = resultSet.getInt("id_cliente");
            int id_produto = resultSet.getInt("id_produto");
            int quantidade = resultSet.getInt("quantidade");

            Pedido pedido = new Pedido(id_produto, quantidade, id_cliente, "Cadastrado", id_pedido);
            pedidos.add(pedido);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao listar pedidos por cliente");
        ex.printStackTrace();
    }

    return pedidos;
}

public List<Pedido> listarPedidosPorProduto(String nomeProduto) throws SQLException {
    List<Pedido> pedidos = new ArrayList<>();

    String sql = "SELECT p.* FROM pedido p JOIN produto pr ON p.id_produto = pr.id_produto WHERE pr.nome = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nomeProduto);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int id_pedido = resultSet.getInt("codigo");
            int id_cliente = resultSet.getInt("id_cliente");
            int id_produto = resultSet.getInt("id_produto");
            int quantidade = resultSet.getInt("quantidade");

            Pedido pedido = new Pedido(id_produto, quantidade, id_cliente, "Cadastrado", id_pedido);
            pedidos.add(pedido);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao listar pedidos por produto");
        ex.printStackTrace();
    }

    return pedidos;
}

public List<Pedido> listarPedidosPorMarca(String nomeMarca) throws SQLException {
    List<Pedido> pedidos = new ArrayList<>();

    String sql = "SELECT p.* FROM pedido p " +
                 "JOIN produto pr ON p.id_produto = pr.id_produto " +
                 "JOIN marca m ON pr.id_marca = m.id_marca " +
                 "WHERE m.nome = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, nomeMarca);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            int id_pedido = resultSet.getInt("codigo");
            int id_cliente = resultSet.getInt("id_cliente");
            int id_produto = resultSet.getInt("id_produto");
            int quantidade = resultSet.getInt("quantidade");

            Pedido pedido = new Pedido(id_produto, quantidade, id_cliente, "Cadastrado", id_pedido);
            pedidos.add(pedido);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao listar pedidos por marca");
        ex.printStackTrace();
    }

    return pedidos;
}

public int findProdutoIdByNome(String nomeProduto) throws SQLException {
        String sql = "SELECT id_produto FROM produto WHERE nome = ?";
        int id_produto = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeProduto);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                id_produto = resultSet.getInt("id_produto");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar ID do produto por nome");
            ex.printStackTrace();
        }

            return id_produto;
    }

}