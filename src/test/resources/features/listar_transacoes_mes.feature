# language: pt
Funcionalidade: Listar transações do mês
  Como usuário
  Quero ver as transações de um mês
  Para acompanhar meus gastos

  Cenário: Listar transações de um mês com lançamentos
    Dado que eu cadastro uma despesa de 100.00 em "2026-11-05" com descrição "Almoço"
    E eu cadastro uma despesa de 200.00 em "2026-10-05" com descrição "Cinema"
    Quando eu listo as transações do mês 11
    Então devem ser encontradas 1 transações

  Cenário: Listar mês sem lançamentos
    Quando eu listo as transações do mês 13
    Então o sistema deve rejeitar a consulta com a mensagem "Mês inválido"