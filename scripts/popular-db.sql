INSERT INTO Setor (ID_SETOR, NOME)
VALUES (1, 'Desenvolvimento'),
(2, 'Infraestrutura'),
(3, 'Segurança'),
(4, 'Vendas');

INSERT INTO Funcionario (ID_FUNCIONARIO, NOME, NU_SALARIO, DS_EMAIL, NU_IDADE, FK_SETOR)
VALUES (1, 'Teste', 5000.00, 'teste@teste.com', 24, 1);