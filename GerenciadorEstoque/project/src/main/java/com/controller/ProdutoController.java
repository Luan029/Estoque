package com.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.model.dao.ConexaoDao;
import com.model.dao.ProdutoDao;
import com.model.entidades.Produto;

public class ProdutoController {
    private static Connection connection = ConexaoDao.conectar();
    private static Produto produto;
    private static ProdutoDao produtoDao = new ProdutoDao(connection);
    private static Scanner scanner = new Scanner(System.in);

    public static Produto cadastrarProduto() throws SQLException{

        try {
        System.out.println("-----------------------");
        System.out.println("* Cadastro de produto *");
        System.out.println("-----------------------");    
        System.out.println("Informe o nome do produto");
        String nome = scanner.nextLine();

        System.out.println("Quatidade do produto");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Informe o nome da marca do produto:");
        String nomeMarca = scanner.nextLine();

        int idMarca = buscarIdMarcaPorNome(nomeMarca);

        if (idMarca == 0) {
            System.out.println("A marca informada não existe.");
            return null; 
        }

        produto = new Produto(nome, idMarca, quantidade);

        produtoDao.insert(produto);
    
        return produto;

        } catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static void atualizarProduto() throws SQLException {

        try {
            System.out.println("-----------------------");
            System.out.println("* Atualizar produto *");
            System.out.println("-----------------------");    
            System.out.println("Informe o nome do produto que deseja atualizar:");
            String nomeProduto = scanner.nextLine();
    
            System.out.println("Informe o novo nome do produto:");
            String novoNome = scanner.nextLine();
    
            System.out.println("Informe a nova quantidade do produto:");
            int novaQuantidade = scanner.nextInt();
            scanner.nextLine(); 
    
            System.out.println("Informe o novo nome da marca do produto:");
            String novoNomeMarca = scanner.nextLine();
    
            int idNovaMarca = buscarIdMarcaPorNome(novoNomeMarca);
    
            if (idNovaMarca == 0) {
                System.out.println("A nova marca informada não existe.");
                return;
            }
    
            produtoDao.update(nomeProduto, novoNome, novaQuantidade, idNovaMarca);
    
            System.out.println("Produto atualizado com sucesso!");
    
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarProdutos() throws SQLException {
        try {
            List<Produto> produtos = produtoDao.findAllProdutos();

            if (!produtos.isEmpty()) {
                System.out.println("-----------------------");
                System.out.println("Lista de Produtos:");
                System.out.println("-----------------------");    

                for (Produto produto : produtos) {
                    System.out.println("Código: " + produto.getCodigo());
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Quantidade: " + produto.getQuantidade());
                    System.out.println("Marca: " + buscarNomeMarcaPorId(produto.getId_marca()));
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum produto encontrado.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void excluirProduto() throws SQLException {
        System.out.println("-----------------------");
        System.out.println("* Excluir produto *");
        System.out.println("-----------------------");    
        System.out.println("Informe o nome do produto que deseja excluir:");
        String nome = scanner.nextLine();
    
        produtoDao.delete(nome);
    
        System.out.println("Produto excluído com sucesso!");
    }

    public static void listarProdutosPorNomeMarca() throws SQLException {
        try {
            System.out.println("Informe o nome da marca para listar os produtos:");
            String nomeMarca = scanner.nextLine();
    
            List<Produto> produtos = produtoDao.findProdutosPorNomeMarca(nomeMarca);
    
            if (!produtos.isEmpty()) {
                System.out.println("Produtos da marca " + nomeMarca + ":");
    
                for (Produto produto : produtos) {
                    System.out.println("Código: " + produto.getCodigo());
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Quantidade: " + produto.getQuantidade());
                    System.out.println("Marca: " + buscarNomeMarcaPorId(produto.getId_marca()));
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum produto encontrado para a marca " + nomeMarca);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarProdutosPorNome() throws SQLException {
        try {
            System.out.println("Informe o texto a ser pesquisado no nome dos produtos:");
            String texto = scanner.nextLine();
    
            List<Produto> produtos = produtoDao.findProdutosPorNome(texto);
    
            if (!produtos.isEmpty()) {
                System.out.println("Produtos com nomes contendo '" + texto + "':");
    
                for (Produto produto : produtos) {
                    System.out.println("Código: " + produto.getCodigo());
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Quantidade: " + produto.getQuantidade());
                    System.out.println("Marca: " + buscarNomeMarcaPorId(produto.getId_marca()));
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum produto encontrado com o texto '" + texto + "' no nome.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void listarProdutosEmEstoque() throws SQLException {
        try {
            List<Produto> produtos = produtoDao.findAllProdutosEmEstoque();
    
            if (!produtos.isEmpty()) {
                System.out.println("Produtos em estoque:");
    
                for (Produto produto : produtos) {
                    System.out.println("Código: " + produto.getCodigo());
                    System.out.println("Nome: " + produto.getNome());
                    System.out.println("Quantidade: " + produto.getQuantidade());
                    System.out.println("Marca: " + buscarNomeMarcaPorId(produto.getId_marca()));
                    System.out.println("-------------");
                }
            } else {
                System.out.println("Nenhum produto em estoque encontrado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int buscarIdMarcaPorNome(String nomeMarca) throws SQLException {
        int idMarca = produtoDao.findMarcaIdByNome(nomeMarca);
        return idMarca;
    }

    public static String buscarNomeMarcaPorId(int idMarca) throws SQLException {
        String nomeMarca = produtoDao.findNomeMarcaById(idMarca);
        return nomeMarca;
    }
    
}
