# language: pt
Funcionalidade: Calcular saldo
  Como usuário
  Quero ver meu saldo
  Para saber minha situação financeira

  Cenário: Saldo zero com receitas e despesas equivalentes
    Dado que eu cadastrei uma receita de 500.00 em "2026-11-05" com descrição "Auxílio"
    E que eu cadastrei uma despesa de 500.00 em "2026-11-06" com descrição "Aluguel"
    Quando eu calculo o saldo atual
    Então o saldo atual deve ser 0.00

  Cenário: Saldo positivo
    Dado que eu cadastrei uma receita de 3000.00 em "2026-11-05" com descrição "Salário"
    E que eu cadastrei uma despesa de 150.00 em "2026-11-06" com descrição "Mercado"
    Quando eu calculo o saldo atual
    Então o saldo atual deve ser 2850.00

  Cenário: Saldo negativo com receitas e despesas
    Dado que eu cadastrei uma receita de 150.00 em "2026-11-05" com descrição "Venda"
    E que eu cadastrei uma despesa de 200.00 em "2026-11-06" com descrição "Conta de luz"
    Quando eu calculo o saldo atual
    Então o saldo atual deve ser -50.00

