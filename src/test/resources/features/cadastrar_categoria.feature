# language: pt
Funcionalidade: Cadastrar categoria
  Como usuário
  Quero cadastrar categorias financeiras
  Para organizar os tipos de lançamentos do sistema

  Cenário: Cadastrar categoria com sucesso
    Quando eu cadastro a categoria "Alimentação"
    Então a categoria "Alimentação" deve estar cadastrada
    E o sistema deve conter 1 categorias cadastradas

  Cenário: Rejeitar categoria com nome em branco
    Quando eu cadastro a categoria ""
    Então o sistema deve rejeitar a categoria com a mensagem "Nome da categoria não pode ser vazio"

  Cenário: Rejeitar categoria duplicada
    Dado que a categoria "Transporte" já foi cadastrada
    Quando eu cadastro a categoria "Transporte"
    Então o sistema deve rejeitar a categoria com a mensagem "Categoria já cadastrada"