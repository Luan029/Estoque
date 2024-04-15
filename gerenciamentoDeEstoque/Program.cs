﻿using System;
using System.Collections.Generic;
using System.Diagnostics.Contracts;

namespace SistemaGerenciamentoEstoque
{
    public class Produto
    {
        public int Id { get; private set; }
        public string Nome { get; set; }
        public Marca Marca { get; set; }
        public int QuantidadeEstoque { get; set; }

        public Produto(int id, string nome, Marca marca, int quantidadeEstoque)
        {
            Contract.Requires(id > 0);
            Contract.Requires(id.ToString().Length == 5);
            Contract.Requires(!string.IsNullOrEmpty(nome));
            Contract.Requires(marca != null);
            Contract.Requires(quantidadeEstoque >= 0);
            Id = id;
            Nome = nome;
            Marca = marca;
            QuantidadeEstoque = quantidadeEstoque;

            Contract.Ensures(Id > 0);
            Contract.Ensures(Id.ToString().Length == 5);
            Contract.Ensures(!string.IsNullOrEmpty(Nome));
            Contract.Ensures(Marca != null);
            Contract.Ensures(QuantidadeEstoque >= 0);
        }
    }


    public class Marca
    {
        public int Id { get; private set; }
        public string Nome { get; set; }

        public Marca(int id, string nome)
        {
            Contract.Requires(id > 0);
            Contract.Requires(!string.IsNullOrEmpty(nome));
            Contract.Requires(id.ToString().Length == 4);

            Id = id;
            Nome = nome;
            Contract.Ensures(Id > 0);
            Contract.Ensures(Id.ToString().Length == 4);
            Contract.Ensures(!string.IsNullOrEmpty(Nome));
        }
    }

    public class SistemaEstoque
    {
        private List<Produto> produtos = new List<Produto>();
        private List<Marca> marcas = new List<Marca>();

        public void CadastrarProduto(Produto produto, Marca marca)
        {
            Contract.Requires(produto != null);
            Contract.Requires(marca != null);
            Contract.Requires(!ProdutosContemId(produto.Id));
            if (ProdutosContemId(produto.Id))
            {
                Console.WriteLine("Já existe um produto com este ID. Produto não cadastrado.");
                return;
            }

            produto.Marca = marca;
            produtos.Add(produto);

            Contract.Ensures(produtos.Contains(produto));
            Console.WriteLine("Produto cadastrado com sucesso!");
        }


        public void RemoverProduto(int id)
        {
            Contract.Requires(id > 0);
            Contract.Requires(ProdutosContemId(id));
            Produto produto = produtos.Find(p => p.Id == id);
            produtos.Remove(produto);

            Contract.Ensures(!produtos.Contains(produto));
            Console.WriteLine("Produto removido com sucesso!");
        }
        public void EditarProduto(int id)
        {
            if (!ProdutosContemId(id))
            {
                Console.WriteLine("Id de produto não encontrado");
                return;
            }
            Contract.Requires(ProdutosContemId(id));
            Produto produto = produtos.Find(p => p.Id == id);


            Console.WriteLine("O que deseja editar?");
            Console.WriteLine("1. Nome");
            Console.WriteLine("2. Marca");
            Console.WriteLine("3. Quantidade em Estoque");
            Console.WriteLine("4. Cancelar");

            string opcao = Console.ReadLine();
            Console.WriteLine();

            switch (opcao)
            {
                case "1":
                   
                    Console.Write("Informe o novo nome:");
                    string novoNome = Console.ReadLine();

                    Contract.Requires(!string.IsNullOrEmpty(novoNome));
         
                    produto.Nome = novoNome;

                    Contract.Ensures(produto.Nome.Equals(novoNome));
                    Console.Write("Nome do produto atualizado com sucesso!");
                    
                    break;
                case "2":
                    Console.Write("Informe o ID da nova marca:");
                    int novaMarcaId = int.Parse(Console.ReadLine());
                    Marca novaMarca = marcas.Find(m => m.Id == novaMarcaId);

                    Contract.Requires(MarcasContemId(novaMarcaId));
                    if (novaMarca == null)
                    {
                        Console.Write("ID de marca não encontrado. Marca não atualizada.");
                        break;
                    }
                    produto.Marca = novaMarca;
                    Contract.Ensures(produto.Marca == novaMarca);
                    Console.Write("Marca do produto atualizada com sucesso!");
                    break;
                case "3":
                    Console.Write("Informe a nova quantidade em estoque:");
                    int quantidade = int.Parse(Console.ReadLine());

                    Contract.Requires(quantidade >= 0);
                    produto.QuantidadeEstoque = quantidade;

                    Contract.Ensures(produto.QuantidadeEstoque == quantidade);

                    Console.Write("Quantidade em estoque do produto atualizado com sucesso!");
                    break;
                case "4": return;
                default:
                    Console.WriteLine("Opcao invalida");
                    break;
            }
            Contract.Ensures(produto != null);
        }
        public List<Produto> ListarProdutos()
        {
            return produtos;
        }

        public void CadastrarMarca(Marca marca)
        {
            Contract.Requires(marca != null);
            Contract.Requires(!MarcasContemId(marca.Id));
            if (MarcasContemId(marca.Id))
            {
                Console.WriteLine("Já existe uma marca cadastrada com este ID. Marca não cadastrada.");
                return;
            }

            marcas.Add(marca);
            Contract.Ensures(marcas.Contains(marca));
            Console.WriteLine("Marca cadastrada com sucesso!");
        }

        public void RemoverMarca(int id)
        {
            Contract.Requires(id > 0);
            Contract.Requires(MarcasContemId(id));
            Marca marca = marcas.Find(m => m.Id == id);

            marcas.Remove(marca);
            var produtos = ListarProdutos();
            var produtosMarca = new List<Produto>();
            foreach (var produto in produtos)
            {
                if (produto.Marca.Id == id)
                {
                    produtosMarca.Add(produto);
                }
            }
            if (produtosMarca.Count > 0)
            {
                foreach (var produto in produtosMarca)
                {
                    produtos.Remove(produto);
                }
            }
            Contract.Ensures(!marcas.Contains(marca));
            Console.WriteLine("Marca removida com sucesso!");
        }
        public void ListarProdutosPorMarca(int id)
        {

            var produtos = ListarProdutos();
            var produtosMarca = new List<Produto>();
            foreach (var produto in produtos)
            {
                if (produto.Marca.Id == id)
                {
                    produtosMarca.Add(produto);
                }
            }
            if (produtosMarca.Count > 0)
            {
                Console.WriteLine("Produtos da marca com ID " + id + ":");
                foreach (var produto in produtosMarca)
                {
                    Console.WriteLine("Produto:" + produto.Nome +
                        " Id: " + produto.Id +
                        " Quantidade em Estoque: " + produto.QuantidadeEstoque +
                        " Marca:" + produto.Marca.Nome);
                }
            }
            else
            {
                Console.WriteLine("Não encontramos nenhum produto cadastrado a esta marca");
            }
            Contract.Ensures(produtosMarca.Count >= 0);
        }
        public List<Marca> ListarMarcas()
        {
            return marcas;
        }

        private bool ProdutosContemId(int id)
        {
            return produtos.Exists(p => p.Id == id);
        }

        private bool MarcasContemId(int id)
        {
            return marcas.Exists(m => m.Id == id);
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            SistemaEstoque sistemaEstoque = new SistemaEstoque();
            while (true)
            {
                Console.WriteLine("|============ Menu ===========|");
                Console.WriteLine("|1. Cadastrar Produto         |");
                Console.WriteLine("|2. Remover Produto           |");
                Console.WriteLine("|3. Editar Produto            |");
                Console.WriteLine("|4. Listar Produtos           |");
                Console.WriteLine("|5. Cadastrar Marca           |");
                Console.WriteLine("|6. Remover Marca             |");
                Console.WriteLine("|7. Listar Produtos por Marca |");
                Console.WriteLine("|8. Sair                      |");
                Console.WriteLine("|______Escolha uma Opção______|");

                string opcao = Console.ReadLine();
                Console.WriteLine();
                switch (opcao)
                {
                    case "1":
                        CadastrarProduto(sistemaEstoque);
                        break;
                    case "2":
                        RemoverProduto(sistemaEstoque);
                        break;
                    case "3":
                        EditarProduto(sistemaEstoque);
                        break;
                    case "4":
                        ListarProdutos(sistemaEstoque);
                        break;
                    case "5":
                        CadastrarMarca(sistemaEstoque);
                        break;
                    case "6":
                        RemoverMarca(sistemaEstoque);
                        break;
                    case "7":
                        ListarProdutosPorMarca(sistemaEstoque);
                        break;
                    case "8":
                        Console.WriteLine("Saindo...");
                        return;
                    default:
                        Console.WriteLine("Opção inválida. Por favor, escolha novamente.");
                        break;
                }


                Console.WriteLine();

            };
        }
        static void CadastrarProduto(SistemaEstoque sistemaEstoque)
        {
            var marcas = sistemaEstoque.ListarMarcas();
            if (marcas.Count == 0)
            {
                Console.WriteLine("Cadastre pelo menos uma marca no sistema antes de cadastrar um produto.");
                return;
            }
            Console.WriteLine("======= Cadastrar Produto =======");
            string idStr;
            do
            {
                Console.Write("Digite o ID do produto com 5 caracteres: ");
                idStr = Console.ReadLine();
            } while (idStr.Length != 5);

            int id = int.Parse(idStr);

            Console.Write("Digite o nome do produto: ");
            string nome = Console.ReadLine();

            
            Console.WriteLine("Marcas disponíveis:");
            foreach (var marca in marcas)
            {
                Console.WriteLine($"ID: {marca.Id}, Nome: {marca.Nome}");
            }
            Console.Write("Digite o ID da marca do produto: ");
            int marcaId = int.Parse(Console.ReadLine());

            
            Marca marcaSelecionada = marcas.Find(m => m.Id == marcaId);
            if (marcaSelecionada == null)
            {
                Console.WriteLine("Marca não encontrada. Produto não cadastrado.");
                return;
            }

            Console.Write("Digite a quantidade em estoque: ");
            int quantidadeEstoque = int.Parse(Console.ReadLine());

            Produto produto = new Produto(id, nome, marcaSelecionada, quantidadeEstoque);

            Console.WriteLine(" ____________________________________ ");
            Console.WriteLine("|Deseja mesmo cadastrar esse produto?|");
            Console.WriteLine("|1. Sim                              |");
            Console.WriteLine("|2. Não                              |");
            Console.WriteLine("|____________________________________|");

            string opcao = Console.ReadLine();
            Console.WriteLine();
            switch (opcao)
            {
                case "1":
                    sistemaEstoque.CadastrarProduto(produto, marcaSelecionada);
                    break;
                case "2":
                    return;
                default:
                    Console.WriteLine("Opção inválida. Por favor, escolha novamente.");
                    break;
            }
        }

        static void RemoverProduto(SistemaEstoque sistemaEstoque)
        {
            var produtos = sistemaEstoque.ListarProdutos();
            if (produtos.Count == 0)
            {
                Console.WriteLine("Não há produtos cadastrados.");
                return;
            }
            Console.WriteLine("======= Remover Produto =======");
            Console.Write("Digite o ID do produto a ser removido: ");
            int id = int.Parse(Console.ReadLine());

            Console.WriteLine(" __________________________________ ");
            Console.WriteLine("|Deseja mesmo remover esse produto?|");
            Console.WriteLine("|1. Sim                            |");
            Console.WriteLine("|2. Não                            |");
            Console.WriteLine("|__________________________________|");

            string opcao = Console.ReadLine();
            Console.WriteLine();
            switch (opcao)
            {
                case "1":
                    sistemaEstoque.RemoverProduto(id);
                    break;
                case "2":
                    return;
                default:
                    Console.WriteLine("Opção inválida. Por favor, escolha novamente.");
                    break;
            }


        }
        static void EditarProduto(SistemaEstoque sistemaEstoque)
        {
            var produtos = sistemaEstoque.ListarProdutos();
            if (produtos.Count == 0)
            {
                Console.WriteLine("Não há produtos cadastrados.");
                return;
            }
            Console.WriteLine("======= Editar Produto =======");
            Console.Write("Digite o ID do produto a ser editado: ");
            int id = int.Parse(Console.ReadLine());

            sistemaEstoque.EditarProduto(id);
        }
        static void ListarProdutos(SistemaEstoque sistemaEstoque)
        {
            Console.WriteLine("======= Listar Produtos =======");
            var produtos = sistemaEstoque.ListarProdutos();
            if (produtos.Count == 0)
            {
                Console.WriteLine("Não há produtos cadastrados.");
                return;
            }
            foreach (var produto in produtos)
            {
                Console.WriteLine(
                    $"ID: {produto.Id}, " +
                    $"Nome: {produto.Nome}, " +
                    $"Marca: {produto.Marca.Nome}, " +
                    $"Quantidade em Estoque: {produto.QuantidadeEstoque}"
                    );
            }
        }
        static void CadastrarMarca(SistemaEstoque sistemaEstoque)
        {
            Console.WriteLine("======= Cadastrar Marca =======");
            string idStr;
            do
            {
                Console.Write("Digite o ID da marca com 4 caracteres: ");
                idStr = Console.ReadLine();
            } while (idStr.Length != 4);

            int id = int.Parse(idStr);

            Console.Write("Digite o nome da marca:");
            string nome = Console.ReadLine();

            Marca marca = new Marca(id, nome);
            Console.WriteLine(" __________________________________ ");
            Console.WriteLine("|Deseja mesmo cadastrar essa marca?|");
            Console.WriteLine("|1. Sim                            |");
            Console.WriteLine("|2. Não                            |");
            Console.WriteLine("|__________________________________|");

            string opcao = Console.ReadLine();
            Console.WriteLine();
            switch (opcao)
            {
                case "1":
                    sistemaEstoque.CadastrarMarca(marca);
                    break;
                case "2":
                    return;
                default:
                    Console.WriteLine("Opção inválida. Por favor, escolha novamente.");
                    break;
            }
        }
        static void RemoverMarca(SistemaEstoque sistemaEstoque)
        {
            var marcas = sistemaEstoque.ListarMarcas();
            if (marcas.Count == 0)
            {
                Console.WriteLine("Não existe nenhuma marca no sistema.");
                return;
            }
            Console.WriteLine("======= Remover Marca =======");
            Console.Write("Digite o ID da marca a ser removida: ");
            int id = int.Parse(Console.ReadLine());

            Console.WriteLine(" ________________________________ ");
            Console.WriteLine("|Deseja mesmo remover essa marca?|");
            Console.WriteLine("|1. Sim                          |");
            Console.WriteLine("|2. Não                          |");
            Console.WriteLine("|________________________________|");

            string opcao = Console.ReadLine();
            Console.WriteLine();
            switch (opcao)
            {
                case "1":
                    sistemaEstoque.RemoverMarca(id);
                    break;
                case "2":
                    return;
                default:
                    Console.WriteLine("Opção inválida. Por favor, escolha novamente.");
                    break;
            }
        }
        static void ListarProdutosPorMarca(SistemaEstoque sistemaEstoque)
        {
            var marcas = sistemaEstoque.ListarMarcas();
            if (marcas.Count == 0)
            {
                Console.WriteLine("Não existe nenhuma marca no sistema.");
                return;
            }
            Console.WriteLine("==== Listar Produtos por Marca ====");
            Console.WriteLine("Digite o ID da marca");

            int id = int.Parse(Console.ReadLine());
            sistemaEstoque.ListarProdutosPorMarca(id);
        }
    }
}