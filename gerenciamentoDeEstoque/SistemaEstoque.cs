using System;
using System.Collections.Generic;
using System.Diagnostics.Contracts;

namespace SistemaGerenciamentoEstoque
{
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
                case "4":
                    return;
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
}
