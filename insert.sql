use bancoauth;


INSERT INTO usuario (
    id, 
    nome,
    email, 
    senha, 
    cargo, 
    ativo,
    salario,
    data_criacao, 
    data_atualizacao
) VALUES (
    1, -- Gera um ID único automaticamente
    'nome',
    'admin@kernelpanic.com', 
    '$2a$10$16e598HFycun0McHP9SRteQgnvbqhzZNlFkhEoIcmjXnrRRVW5p5q', -- Hash para: senha123
    'ROLE_ADMIN', 
    true, 
    100,
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);

select * from usuario_auth;