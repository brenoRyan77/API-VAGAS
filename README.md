# Gerenciamento de Vagas de Emprego e Candidaturas

## Objetivo
Administrar o sistema de vagas de emprego e candidaturas de forma eficiente por meio da API VAGAS.

## Ator(es)
Recrutadores, administradores de RH, candidatos.

## Pré-condições
- A API VAGAS está acessível.
- Os atores possuem credenciais válidas para autenticação.

## Fluxo Principal

1. **Login e Autenticação**
   - Antes do login, é necessário criar o usuário utilizando o Swagger.
   - Os recrutadores e candidatos fazem login por meio do endpoint `POST /auth/login`, fornecendo suas credenciais.
   - Antes de criar via Swagger, na tabela de perfis, adicionar manualmente os perfis `ROLE_ADMIN` e `ROLE_USER`.
   - O sistema verifica as credenciais e retorna um token de acesso para as operações subsequentes.

2. **Gestão de Vagas**
   - Recrutadores autenticados podem criar novas vagas utilizando o endpoint `POST /vagas`, fornecendo os detalhes da vaga em formato JSON.
   - Também podem listar todas as vagas disponíveis através do endpoint `GET /vagas`.

3. **Detalhes e Atualizações de Vagas**
   - Os recrutadores podem visualizar os detalhes de uma vaga específica utilizando o endpoint `GET /vagas/{id}` ao fornecer o ID da vaga.
   - Para atualizar uma vaga, utilizam o endpoint `PUT /vagas/{id}`, enviando os detalhes atualizados da vaga em formato JSON.
   - Se necessário, podem encerrar uma vaga existente utilizando o endpoint `DELETE /vagas/{id}`.

4. **Candidaturas às Vagas**
   - Candidatos autenticados podem visualizar todas as vagas disponíveis por meio do endpoint `GET /vagas`.
   - Para se candidatar a uma vaga, utilizam o endpoint `POST /candidaturas`, fornecendo os detalhes da candidatura em formato JSON.
   - Podem visualizar suas próprias candidaturas através do endpoint `GET /candidaturas/minhas-candidaturas/{login}`, fornecendo seu nome de usuário.

5. **Cancelamento de Candidatura**
   - Candidatos têm a opção de cancelar uma candidatura utilizando o endpoint `DELETE /candidaturas/desistir/{login}/{idCandidatura}`, fornecendo seu nome de usuário e o ID da candidatura.

## Pós-condições
- Recrutadores podem criar, visualizar e atualizar vagas.
- Candidatos podem visualizar vagas disponíveis e gerenciar suas candidaturas.

## Extensões ou Fluxos Alternativos
- Em caso de credenciais inválidas, os atores não conseguem autenticar-se no sistema.
- Se uma vaga estiver encerrada, não será possível se candidatar a ela.
- Se uma candidatura for cancelada, a mesma não será mais considerada válida no sistema.
