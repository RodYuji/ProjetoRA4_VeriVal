# language: pt
Funcionalidade: Definir teto de gastos
  Como usuário
  Quero definir um limite máximo de despesas para um mês
  Para controlar meus gastos

  Cenário: Definir teto válido
    Quando eu defino o teto de 3000.00 para o mês "2026-11"
    Então o teto do mês "2026-11" deve ser 3000.00

  Cenário: Redefinir o teto de um mês
    Dado que o teto do mês "2026-11" é 3000.00
    Quando eu defino o teto de 3500.00 para o mês "2026-11"
    Então o teto do mês "2026-11" deve ser 3500.00

  Cenário: Teto com valor inválido
    Quando eu defino o teto de 0.00 para o mês "2026-11"
    Então o sistema deve rejeitar com a mensagem "O teto de gastos deve ser maior que zero"