## 🧩 PresenteFácil 1.0 — TP4 (Visualização)

---

### 👥 Participantes

- **Rafael Nascimento Jardim**
- **Welbert Junio Afonso de Almeida**
- **Gustavo Henrique Silva Maciel**
- **Davi Wenzel Cury**

---

### 🛠️ Como Executar

1.  **Baixar a pasta Tp4**
2.  **Executar:** Abrir o arquivo `index.html`

---

## 1. Descrição Geral do Sistema

Este trabalho consiste no desenvolvimento de uma página web interativa para demonstrar o funcionamento completo das operações **CRUD (Create, Read, Update, Delete)** aplicadas ao gerenciamento de produtos. O objetivo é auxiliar alunos da disciplina Algoritmos e Estruturas de Dados III a compreender como dados são armazenados, manipulados e visualizados em um arquivo — simulando o armazenamento real por meio da API **LocalStorage** do navegador.

A aplicação permite:

* Cadastrar produtos
* Listar os produtos armazenados
* Consultar produtos por nome ou ID
* Editar produtos já cadastrados
* Excluir produtos
* Persistência automática dos dados no LocalStorage

---

## 2. Tecnologias

A interface foi criada com **HTML, CSS e JavaScript puros**, sem bibliotecas externas.

---

## 3. Estrutura do Sistema (Classes / Arquitetura)

A aplicação foi estruturada da seguinte forma:

| Arquivo | Descrição |
| :--- | :--- |
| **`index.html`** | Contém toda a estrutura da interface, incluindo formulários de cadastro/edição e tabela de visualização dos produtos. |
| **`styles.css`** | Contém as regras de estilo para organização visual da página. |
| **`script.js`** | Responsável por: Gerenciar o vetor de produtos, controlar o incremento de IDs, implementar todas as operações CRUD, atualizar o LocalStorage, exibir mensagens de sucesso/erro e atualizar a tabela principal. |

---

### Funcionalidades Implementadas

| Funcionalidade | Descrição |
| :--- | :--- |
| Criar produto | Adiciona um novo produto ao LocalStorage. |
| Consultar | Busca por ID ou nome do produto. |
| Editar | Permite modificar qualquer campo do produto. |
| Excluir | Remove o produto permanentemente. |
| Persistência local | Dados salvos mesmo após fechar a página (LocalStorage). |

---

## 4. Roteiro de Testes Usado com os Alunos

1.  **Cadastrar:** Crie um produto chamado `kindle paperwhite`, Pessoa `Ana Costa`, categoria `eletrônica`, prioridade `alta` e um comentário.
2.  **Localizar:** Localize o produto cadastrado (busca ou lista).
3.  **Atualizar:** Atualize a prioridade, categoria e nome do produto.
4.  **Excluir:** Exclua o produto.
5.  **Verificar:** Verifique se ele saiu da lista.

---

## 5. Afirmativas Usadas (Escala Likert: 1 a 5)

| Item | Afirmativa resumida | Média |
| :---: | :--- | :---: |
| 1 | Compreensão do CRUD | 4,8 |
| 2 | Facilidade de uso | 4,6 |
| 3 | Intuitividade | 4,8 |
| 4 | Clareza das mensagens | 4,4 |
| 5 | Visualização dos dados | 4,8 |
| 6 | Edição/exclusão | 4,6 |
| 7 | Fluxo natural | 4,8 |
| 8 | Interface agradável | 4,6 |

> A maior parte dos usuários considerou a aplicação eficiente e satisfatória, destacando a clareza das mensagens e a boa visualização dos dados.

---

## 6. Verificação de Entrega

| Pergunta | Resposta | Justificativa |
| :--- | :--- | :--- |
| A página web com a visualização interativa do CRUD de produtos foi criada? | **Sim** | Todas as operações CRUD estão implementadas. |
| Há um vídeo de até 3 minutos demonstrando o uso da visualização? | **Sim** | Vídeo gravado e disponibilizado no repositório/YouTube. |
| O trabalho foi criado apenas com HTML, CSS e JS? | **Sim** | Não utilizamos frameworks. |
| O relatório do trabalho foi entregue no APC? | **Sim** | O relatório está incluído no repositório e na entrega. |
| O trabalho está completo e funcionando sem erros de execução? | **Sim** | Todos os testes foram realizados com sucesso. |
| O trabalho é original e não é cópia de outro grupo? | **Sim** | Todo o código foi produzido para este TP. |

