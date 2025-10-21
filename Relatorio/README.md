# 🎁 PresenteFácil 1.0

### Trabalho Prático 1 e 2 — AEDs III

Sistema de Gerenciamento de Listas de Presentes

---

## 👥 Participantes

* **Rafael Nascimento Jardim**
* **Welbert Junio Afonso de Almeida**
* **Gustavo Henrique Silva Maciel**
* **Davi Wenzel Cury**

---

## 🧠 Descrição do Sistema

O **PresenteFácil** é um sistema de gerenciamento de listas de presentes, onde usuários podem criar listas personalizadas, compartilhá-las com amigos e familiares através de um **código único (NanoID)** e gerenciar seus dados de forma segura e persistente.

O projeto foi desenvolvido em **Java**, utilizando **arquivos indexados, Tabela Hash Extensível e Árvores B+** como estruturas de dados persistentes.

---

## 🚀 Funcionalidades Principais

* ✅ **Cadastro e login de usuários** com senha criptografada (SHA-256)
* 🔐 **Recuperação de senha** via pergunta e resposta secreta
* 📝 **Criação, listagem, atualização e exclusão** de listas de presentes
* 🧩 **Geração automática de código compartilhável (NanoID)**
* 🔎 **Busca de listas** de outros usuários através do código
* 🗂️ **Associação N:N entre Listas e Produtos**
* 🛍️ **CRUD completo de produtos** com GTIN-13 e inativação de itens
* 📦 **Gestão de produtos dentro das listas** (quantidade e observações)

---

## 💾 Estrutura de Dados

```
dados/
 ├── usuarios.db                 → Arquivo principal de usuários
 ├── usuarios.email.idx_{d,c}.db → Índice hash extensível de e-mails
 ├── listas.db                   → Arquivo principal de listas
 ├── listas.usuario.idx.db       → Índice árvore B+ (usuário → lista)
 ├── produtos.db                 → Arquivo principal de produtos
 ├── listaproduto.db             → Arquivo de relacionamento N:N (lista ↔ produto)
 ├── listaproduto.idxLista.db    → Índice B+ (idLista → idListaProduto)
 ├── listaproduto.idxProduto.db  → Índice B+ (idProduto → idListaProduto)
```

---

## 🧩 Classes Criadas

### **1. Modelagem de Dados**

* `Usuario` → representa um usuário do sistema
* `Lista` → representa uma lista de presentes
* `Produto` → representa um produto com GTIN-13, nome, descrição e status ativo/inativo
* `ListaProduto` → entidade associativa (N:N) entre lista e produto

---

### **2. Armazenamento e Índices**

* `ArquivoUsuario` → CRUD de usuários com **hash extensível (e-mail → ID)**
* `ArquivoLista` → CRUD de listas com **árvore B+ (idUsuario → idLista)**
* `ArquivoProduto` → CRUD de produtos com **índice GTIN e status ativo/inativo**
* `ArquivoListaProduto` → CRUD de relacionamentos N:N entre listas e produtos
* `ParEmailID`, `ParIntInt` → estruturas auxiliares de índice

---

### **3. Controle e Visão**

* `ControlePrincipal`, `ControleUsuario`, `ControleLista`, `ControleProduto` → controle do fluxo da aplicação
* `VisaoUsuario`, `VisaoLista`, `VisaoProduto` → camadas de interação com o usuário

---

### **4. Estruturas de Dados Persistentes**

* `HashExtensivel` → índice direto (e-mail → ID)
* `ArvoreBMais` → índice indireto (idUsuario → idLista)
* `ParIDEndereco` → par (ID, endereço) para índices primários

---

## 🔄 Operações Especiais

### 🔐 Recuperação de Senha

* Pergunta secreta e resposta armazenadas de forma segura
* Validação e redefinição da senha criptografada

### 🧾 Geração de Código Compartilhável

* Cada lista recebe automaticamente um **NanoID único**
* O código permite acesso público à lista sem precisar de login

### 🗃️ Relacionamento N:N (Lista ↔ Produto)

* Implementado através da entidade `ListaProduto`
* Cada relação armazena:

  * ID da Lista
  * ID do Produto
  * Quantidade
  * Observações

---

## 🖥️ Telas do Sistema

* Tela inicial
* Cadastro de novo usuário
* Login
* Menu principal
* Meus dados
* Minhas listas
* Nova lista
* Buscar lista pelo código
* Gerenciar produtos na lista

---

## ✅ Checklist TP2

| Requisito                                 | Atendido | Observação                                           |
| ----------------------------------------- | -------- | ---------------------------------------------------- |
| CRUD de usuários com índices              | ✅        | Hash extensível funcionando                          |
| CRUD de listas com índices                | ✅        | Árvore B+ idUsuario→idLista                          |
| CRUD de produtos (GTIN-13)                | ✅        | Implementado e testado                               |
| Entidade de associação ListaProduto (N:N) | ✅        | Persistência e índices B+                            |
| Gestão de produtos dentro das listas      | ✅        | Adicionar, remover, alterar quantidade e observações |
| Inativação de produtos                    | ✅        | Impede associação a novas listas                     |
| Visualização de listas por código NanoID  | ✅        | Funcionando corretamente                             |
| Integridade referencial mantida           | ✅        | Exclusões sincronizadas                              |
| Código compila e executa sem erros        | ✅        | Testado no Ubuntu 22.04                              |
| Originalidade                             | ✅        | Código próprio do grupo                              |

---

## 🏁 Conclusão

O sistema **PresenteFácil** evoluiu com sucesso do **TP1 (relacionamento 1:N)** para o **TP2 (relacionamento N:N)**.
O código apresenta:

* Estruturas de dados persistentes eficientes (Hash Extensível e Árvores B+)
* Relacionamento completo entre listas e produtos
* Interface funcional em modo texto, totalmente integrada.

O projeto está **funcional para avaliação**.

---

## 📹 Vídeo de Demonstração

O vídeo de até **3 minutos** mostra:

* Cadastro e login
* Criação e listagem de listas
* Cadastro de produtos
* Associação de produtos às listas
* Busca de lista por código

> 📽️ [Vídeo de Apresentação TP1](https://youtu.be/NpyloV69Be0)
> 📽️ [Vídeo de Apresentação TP2](https://www.youtube.com/watch?si=7E3g5fbp5sFOmfiV&v=s9rRcJV1k44&feature=youtu.be)

