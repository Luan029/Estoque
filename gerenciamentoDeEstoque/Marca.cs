using System;
using System.Diagnostics.Contracts;

namespace SistemaGerenciamentoEstoque
{
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
}
