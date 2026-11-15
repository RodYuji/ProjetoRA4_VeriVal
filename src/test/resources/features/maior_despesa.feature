# language: pt
Funcionalidade: Consultar maior despesa
  Como usuário
  Quero saber qual foi meu maior gasto
  Para identificar onde estou gastando mais

  Cenário: Maior despesa entre várias
    Dado que eu cadastrei uma despesa de 150.00 em "2026-11-07" com descrição "Mercado"
    E que eu cadastrei uma despesa de 800.00 em "2026-11-08" com descrição "Aluguel"
    E que eu cadastrei uma despesa de 50.00 em "2026-11-09" com descrição "Farmácia"
    Quando eu consulto a maior despesa
    Então a maior despesa deve ser 800.00

  Cenário: Única despesa
    Dado que eu cadastrei uma despesa de 120.00 em "2026-11-07" com descrição "Amazon"
    Quando eu consulto a maior despesa
    Então a maior despesa deve ser 120.00

  Cenário: Nenhuma despesa cadastrada
    Dado que nenhuma transação foi cadastrada
    Quando eu consulto a maior despesa
    Então o sistema deve informar que não há despesas cadastradas