# language: pt
Funcionalidade: Cadastrar despesa
  Como usuário
  Quero registrar meus gastos
  Para controlar minhas finanças

  Cenário: Cadastrar despesa válida
    Quando eu cadastro uma despesa de 150.00 em "2026-11-06" com descrição "Mercado"
    Então devem existir 1 transações cadastradas

  Cenário: Despesa com valor inválido
    Quando eu cadastro uma despesa de 0.00 em "2026-11-06" com descrição "Mercado"
    Então o sistema deve rejeitar com a mensagem "Valor deve ser maior que zero"