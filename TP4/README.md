# 🧩 PresenteFácil 1.0 — TP4 (Visualização)

---

## 👥 Participantes

- **Rafael Nascimento Jardim**
- **Welbert Junio Afonso de Almeida**
- **Gustavo Henrique Silva Maciel**
- **Davi Wenzel Cury**
---
## 🛠️ Como Executar
### 🔄 1. Baixar a pasta Tp4
### ▶️ 2. Executar
  Abrir o arquivo index.html
  
# 🧩 PresenteFácil 1.0 — TP4 (Visualização)
1. Descrição Geral do Sistema

Este trabalho consiste no desenvolvimento de uma página web interativa para demonstrar o funcionamento completo das operações CRUD (Create, Read, Update, Delete) aplicadas ao gerenciamento de produtos. O objetivo é servir como uma ferramenta extensionista que auxilie alunos da disciplina Algoritmos e Estruturas de Dados III a compreender como dados são armazenados, manipulados e visualizados em um arquivo — simulando o armazenamento real por meio da API LocalStorage do navegador.

A aplicação permite:

-Cadastrar produtos

=Listar os produtos armazenados

=Consultar produtos por nome ou ID

=Editar produtos já cadastrados

=Excluir produtos

=Persistência automática dos dados no LocalStorage

2.A interface foi criada com HTML, CSS e JavaScript puros, sem bibliotecas externas.

3. Estrutura do Sistema (Classes / Arquitetura)

A aplicação foi estruturada da seguinte forma:

Arquivo principal

index.html
Contém toda a estrutura da interface, incluindo formulários de cadastro/edição e tabela de visualização dos produtos.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Arquivo de estilo

styles.css
Contém as regras de estilo para organização visual da página.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Arquivo de lógica

script.js
Responsável por:
Gerenciar o vetor de produtos
Controlar o incremento de IDs
Implementar todas as operações CRUD
Atualizar o LocalStorage
Exibir mensagens de sucesso/erro

Atualizar a tabela principal
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Funcionalidades implementadas:
  Funcionalidade	Descrição
  Criar produto	Adiciona um novo produto ao LocalStorage
  Consultar	Busca por ID,nome,nome do produto
  Editar	Permite modificar qualquer campo do produto
  Excluir	Remove o produto permanentemente
  Persistência local	Dados salvos mesmo após fechar a página
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
4.Roteiro de Testes Usado com os Alunos

Cadastre um produto chamado kindle paperwhite, Pessoa Ana Costa, categoria eletrônica,prioridade alta e um comentario.

Localize o produto cadastrado.

Atualize a proridade do produto,categoria,nome e prioridade.

Exclua o produto.

Verifique se ele saiu da lista.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
5.Afirmativas usadas (escala Likert: 1 a 5)

1.A aplicação facilita a compreensão do funcionamento do CRUD.

2.As funções principais são fáceis de localizar.

3.O sistema é intuitivo, mesmo para quem nunca usou.

4.As mensagens são claras e úteis.

5.A visualização dos produtos ajuda a entender como os dados são armazenados.

6.A edição e exclusão de registros é simples de realizar.

7.O fluxo das tarefas é natural e sem ambiguidades.

8.A aparência da interface contribui para uma boa experiência de uso.


| Item | Afirmativa resumida    | Média |
| ---- | ---------------------- | ----- |
| 1    | Compreensão do CRUD    | 4,8   |
| 2    | Facilidade de uso      | 4,6   |
| 3    | Intuitividade          | 4,8   |
| 4    | Clareza das mensagens  | 4,4   |
| 5    | Visualização dos dados | 4,8   |
| 6    | Edição/exclusão        | 4,6   |
| 7    | Fluxo natural          | 4,8   |
| 8    | Interface agradável    | 4,6   |
A maior parte dos usuários considerou a aplicação eficiente e satisfatória, destacando a clareza das mensagens e a boa visualização dos dados.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
6.Pergunta                                                                   | Resposta | Justificativa                                           |
| -------------------------------------------------------------------------- | -------- | ------------------------------------------------------- |
| A página web com a visualização interativa do CRUD de produtos foi criada? | **Sim**  | Todas as operações CRUD estão implementadas.            |
| Há um vídeo de até 3 minutos demonstrando o uso da visualização?           | **Sim**  | Vídeo gravado e disponibilizado no repositório/YouTube. |
| O trabalho foi criado apenas com HTML, CSS e JS?                           | **Sim**  | Não utilizamos frameworks.                              |
| O relatório do trabalho foi entregue no APC?                               |          | O relatório está incluído no repositório e na entrega.  |
| O trabalho está completo e funcionando sem erros de execução?              | **Sim**  | Todos os testes foram realizados com sucesso.           |
| O trabalho é original e não é cópia de outro grupo?                        | **Sim**  | Todo o código foi produzido para este TP.               |


