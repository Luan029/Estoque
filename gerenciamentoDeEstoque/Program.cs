using System;
using SistemaGerenciamentoEstoque;

namespace SistemaGerenciamentoEstoque
{
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
            }
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
