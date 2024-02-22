package com.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.entidades.Produto;

public class ProdutoDao {

    private Connection connection;

    public ProdutoDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void insert(Produto produto) throws SQLException{
        String sql = "INSERT INTO produto(nome, quantidade, id_marca) VALUES(?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setObject(3, produto.getId_marca());

            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar inserir produto: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void delete(String nome) throws SQLException {
        String sql = "DELETE FROM produto WHERE nome = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar excluir produto por nome");
            ex.printStackTrace();
        }
    }

    public void update(String nomeProduto, String novoNome, int novaQuantidade, int idNovaMarca) throws SQLException {
        String sql = "UPDATE produto SET nome=?, quantidade=?, id_marca=? WHERE nome=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, novaQuantidade);
            stmt.setInt(3, idNovaMarca);
            stmt.setString(4, nomeProduto);
        
            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao tentar atualizar produto");
            ex.printStackTrace();
        }
    }

    public List<Produto> findAllProdutos() throws SQLException {
        String sql = "SELECT * FROM produto ORDER BY quantidade DESC";
        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int codigo = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome");
                int idMarca = resultSet.getInt("id_marca");
                int quantidade = resultSet.getInt("quantidade");

                Produto produto = new Produto(nome, idMarca, quantidade);
                produto.setCodigo(codigo);

                produtos.add(produto);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produtos");
            ex.printStackTrace();
        }

        return produtos;
    }

    public List<Produto> findProdutosPorNomeMarca(String nomeMarca) throws SQLException {
        String sql = "SELECT * FROM produto WHERE id_marca IN (SELECT id_marca FROM marca WHERE nome = ?) ORDER BY quantidade DESC";
        List<Produto> produtos = new ArrayList<>();
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeMarca);
            ResultSet resultSet = stmt.executeQuery();
    
            while (resultSet.next()) {
                int codigo = resultSet.getInt("id_produto");
                String nomeProduto = resultSet.getString("nome");
                int idMarca = resultSet.getInt("id_marca");
                int quantidade = resultSet.getInt("quantidade");
    
                Produto produto = new Produto(nomeProduto, idMarca, quantidade);
                produto.setCodigo(codigo);
    
                produtos.add(produto);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produtos por nome da marca");
            ex.printStackTrace();
        }
    
        return produtos;
    }

    public List<Produto> findProdutosPorNome(String texto) throws SQLException {
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";
        List<Produto> produtos = new ArrayList<>();
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + texto + "%");
            ResultSet resultSet = stmt.executeQuery();
    
            while (resultSet.next()) {
                int codigo = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome");
                int idMarca = resultSet.getInt("id_marca");
                int quantidade = resultSet.getInt("quantidade");
    
                Produto produto = new Produto(nome, idMarca, quantidade);
                produto.setCodigo(codigo);
    
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produtos por nome");
            ex.printStackTrace();
        }
    
        return produtos;
    }

    public int findQuantidadeById(int id_produto) throws SQLException {
        String sql = "SELECT quantidade FROM produto WHERE id_produto = ?";
        int quantidadeEmEstoque = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_produto);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                quantidadeEmEstoque = resultSet.getInt("quantidade");
            }

        } catch (Exception ex) {
            System.out.println("Erro ao tentar verificar estoque do produto");
        }

        return quantidadeEmEstoque;
    }

    public void atualizaEstoque(int id_produto, int quantidade){
        String sql = "UPDATE produto SET quantidade = ? WHERE id_produto = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, id_produto);
            
            stmt.executeUpdate();
            
            System.out.println("Estoque atualizado com sucesso.");
            
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar estoque do produto: " + ex.getMessage());
        }
    }

    public List<Produto> findAllProdutosEmEstoque() throws SQLException {
        String sql = "SELECT * FROM produto WHERE quantidade > 0 ORDER BY quantidade DESC";
        List<Produto> produtos = new ArrayList<>();
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
    
            while (resultSet.next()) {
                int codigo = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome");
                int idMarca = resultSet.getInt("id_marca");
                int quantidade = resultSet.getInt("quantidade");
    
                Produto produto = new Produto(nome, idMarca, quantidade);
                produto.setCodigo(codigo);
    
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produtos em estoque");
            ex.printStackTrace();
        }
    
        return produtos;
    }

    public String findNomeMarcaById(int idMarca) throws SQLException {
        String sql = "SELECT nome FROM marca WHERE id_marca = ?";
        String nomeMarca = "";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMarca);
            ResultSet resultSet = stmt.executeQuery();
    
            if (resultSet.next()) {
                nomeMarca = resultSet.getString("nome");
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar nome da marca por ID");
            ex.printStackTrace();
        }
    
        return nomeMarca;
    }

    public int findMarcaIdByNome(String nomeMarca) throws SQLException {
        String sql = "SELECT id_marca FROM marca WHERE nome = ?";
        int id_marca = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nomeMarca);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                id_marca = resultSet.getInt("id_marca");
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar ID da marca por nome");
            ex.printStackTrace();
        }

            return id_marca;
    }
    
    public Produto findById(int id) throws SQLException {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        Produto produto = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                produto = new Produto(
                    resultSet.getString("nome"),
                    resultSet.getInt("id_marca"),
                    resultSet.getInt("quantidade")
                );
                produto.setCodigo(resultSet.getInt("id_produto"));
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto por ID: " + ex.getMessage());
            throw ex;
        }

        return produto;
    }

}
