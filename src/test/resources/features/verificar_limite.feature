# language: pt
Funcionalidade: Verificar alerta de limite excedido
  Como usuário
  Quero saber se minhas despesas do mês ultrapassaram o teto
  Para tomar providências a tempo

  Contexto:
    Dado que o teto do mês "2026-11" é 3000.00

  Cenário: Despesas acima do teto
    Dado que existe uma despesa de 3200.00 em "2026-11-10"
    Quando eu verifico o limite do mês "2026-11"
    Então o limite deve estar excedido em 200.00

  Cenário: Despesas abaixo do teto
    Dado que existe uma despesa de 2000.00 em "2026-11-10"
    Quando eu verifico o limite do mês "2026-11"
    Então o limite não deve estar excedido

  Cenário: Despesas exatamente iguais ao teto
    Dado que existe uma despesa de 3000.00 em "2026-11-10"
    Quando eu verifico o limite do mês "2026-11"
    Então o limite não deve estar excedido

  Cenário: Despesas de outros meses são ignoradas
    Dado que existe uma despesa de 5000.00 em "2026-10-15"
    E que existe uma despesa de 1000.00 em "2026-11-15"
    Quando eu verifico o limite do mês "2026-11"
    Então o limite não deve estar excedido

  Cenário: Mês sem teto definido
    Quando eu verifico o limite do mês "2026-12"
    Então o sistema deve rejeitar com a mensagem "Não há teto de gastos definido para o mês"