package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.model.dao.ConexaoDao;
import com.model.dao.MarcaDao;
import com.model.entidades.Marca;

public class MarcaController {
    private static Connection connection = ConexaoDao.conectar();
    private static Marca marca;
    private static MarcaDao marcaDao = new MarcaDao(connection);
    private static Scanner scanner = new Scanner(System.in);

    public static Marca cadastroMarca() throws SQLException{

        try {
        System.out.println("-----------------------");
        System.out.println("* Cadastro de marca *");
        System.out.println("-----------------------");    

        System.out.println("Informe o nome da marca");
        String nome = scanner.nextLine();

        System.out.println("Informe o dia que os pedidos devem ser enviados");
        int diaPedido = scanner.nextInt();

        marca = new Marca(nome, diaPedido, 0);

        marcaDao.insert(marca);

        return marca;
        } catch (SQLException ex){
            ex.printStackTrace();
            return null;

        }  
    }
    public static void excluirMarca() throws SQLException {
        System.out.println("-----------------------");
        System.out.println("* Excluir marca *");
        System.out.println("-----------------------");    
        System.out.println("Informe o nome da marca que deseja excluir:");
        String nome = scanner.nextLine();
    
        marcaDao.delete(nome);
    
        System.out.println("marca excluída com sucesso!");
    }
    public static void atualizarMarca() throws SQLException {
        try {
            System.out.println("-----------------------");
            System.out.println("* Atualizar marca *");
            System.out.println("-----------------------");    
            System.out.println("Informe o nome da marca que deseja atualizar:");
            String nomeMarca = scanner.nextLine();
    
            System.out.println("Informe o novo nome da  marca:");
            String novoNome = scanner.nextLine();

            System.out.println("Informe o novo dia que os pedidos devem ser enviados");
            int novoDiaPedido = scanner.nextInt();
        
    
            marcaDao.update(nomeMarca, novoNome, novoDiaPedido);

           System.out.println("Marca atualizada com sucesso!");

        } catch (SQLException ex) {
             ex.printStackTrace();
        }    
    } 
    public static List<Marca> findAllMarcas() throws SQLException {
    
         String sql = "SELECT * FROM marca";
        List<Marca> marcas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int idMarca = resultSet.getInt("id_marca");
                String nome = resultSet.getString("nome");
                int diaPedido = resultSet.getInt("diaenvio");
            
        
                Marca marca = new Marca(nome, diaPedido, idMarca);
                marcas.add(marca);
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao buscar marcas");
            ex.printStackTrace();
        }
        for (Marca marca : marcas) {
            System.out.println("ID: " + marca.getCodMarca() + ", Nome: " + marca.getNome() + ", Dia de Envio: " + marca.getDiaPedido());
        }
      
        return marcas;
    }     
}
