INSERT INTO usuario (id, nome, email, senha, cargo, ativo, salario, gerente_id, data_criacao, data_atualizacao)
VALUES 
(1, 'Gestor', 'gestor@kernelpanic.com', '$2a$10$16e598HFycun0McHP9SRteQgnvbqhzZNlFkhEoIcmjXnrRRVW5p5q', 'ROLE_GESTOR', true, '100', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Financeiro', 'financeiro@kernelpanic.com', '$2a$10$16e598HFycun0McHP9SRteQgnvbqhzZNlFkhEoIcmjXnrRRVW5p5q', 'ROLE_FINANCEIRO', true, '100', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Profissional', 'profissional@kernelpanic.com', '$2a$10$16e598HFycun0McHP9SRteQgnvbqhzZNlFkhEoIcmjXnrRRVW5p5q', 'ROLE_PROFISSIONAL', true, '100', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);