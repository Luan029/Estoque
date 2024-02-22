package com.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.model.entidades.Marca;

public class MarcaDao {
    
    private Connection connection;

    public MarcaDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void insert(Marca marca) throws SQLException{
        String sql = "INSERT INTO marca(nome, diaenvio) VALUES(?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getDiaPedido());
            System.out.println("Marca cadastrada");

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro a tentar inserir marca");
        }
    }
    public void delete(String nome) throws SQLException{
        String sql = "DELETE FROM marca WHERE nome = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException ex){
            System.out.println("Erro ao tenta excluir marca por nome");
            ex.printStackTrace();
        }
    }
  
    public void update(String nomeMarca, String novoNome, int novoDiaPedido) throws SQLException {
        String sql = "UPDATE marca SET nome=?, diaenvio=? WHERE  nome=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, novoDiaPedido);
            stmt.setString(3, nomeMarca);
        
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar atualizar marca");
            ex.printStackTrace();
        }
    }

    public void findAllMarcas(int idMarca, String nome, int diaPedido) throws SQLException {
        String sql = "UPDATE marca SET idMarca=?, nome=? WHERE  diaenvio=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idMarca);
            stmt.setString(2, nome);
            stmt.setInt(3, diaPedido);
        
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao tentar lista marca");
            ex.printStackTrace();
        }
    }
}
