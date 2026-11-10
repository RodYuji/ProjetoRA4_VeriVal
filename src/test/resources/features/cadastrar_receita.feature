# language: pt
Funcionalidade: Cadastrar receita
  Como usuário
  Quero registrar entradas de dinheiro
  Para controlar minhas finanças

  Cenário: Cadastrar receita válida
    Quando eu cadastro uma receita de 3000.00 em "2026-11-05" com descrição "Salário"
    Então devem existir 1 transações cadastradas

  Cenário: Receita com valor inválido
    Quando eu cadastro uma receita de 0.00 em "2026-11-05" com descrição "Salário"
    Então o sistema deve rejeitar com a mensagem "Valor deve ser maior que zero"