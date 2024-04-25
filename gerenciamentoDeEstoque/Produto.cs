using System;
using System.Diagnostics.Contracts;

namespace SistemaGerenciamentoEstoque
{
    public class Produto
    {
        public int Id { get; private set; }
        public string Nome { get; set; }
        public double Preco { get; set; }
        public Marca Marca { get; set; }
        public int QuantidadeEstoque { get; set; }

        public Produto(int id, string nome, Marca marca, int quantidadeEstoque, double preco)
        {
            Contract.Requires(id > 0);
            Contract.Requires(id.ToString().Length == 5);
            Contract.Requires(!string.IsNullOrEmpty(nome));
            Contract.Requires(marca != null);
            Contract.Requires(quantidadeEstoque >= 0);
            Contract.Requires(preco > 0.0);
            Preco = preco;
            Id = id;
            Nome = nome;
            Marca = marca;
            QuantidadeEstoque = quantidadeEstoque;

            Contract.Ensures(Id > 0);
            Contract.Ensures(Id.ToString().Length == 5);
            Contract.Ensures(!string.IsNullOrEmpty(Nome));
            Contract.Ensures(Marca != null);
            Contract.Ensures(QuantidadeEstoque >= 0);
            Contract.Ensures(Preco > 0.0);
        }

        [ContractInvariantMethod]
        protected void ObjectInvariant()
        {
            Contract.Invariant(Id > 0);
            Contract.Invariant(Id.ToString().Length == 5);
            Contract.Invariant(!string.IsNullOrEmpty(Nome));
            Contract.Invariant(Marca != null);
            Contract.Invariant(QuantidadeEstoque >= 0);
            Contract.Invariant(Preco > 0.0);
        }
    }
}
