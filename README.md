# 🧩 PresenteFácil 1.0 — TP3 (Índice Invertido)

---

## 👥 Participantes

- **Rafael Nascimento Jardim**
- **Welbert Junio Afonso de Almeida**
- **Gustavo Henrique Silva Maciel**
- **Davi Wenzel Cury**
---

## 🛠️ Como Compilar e Executar

### ✅ Requisito

- **JDK 17+ instalado**

### 🔄 1. Limpar e Compilar (Visual studio Code)

```bash
rm -rf bin dados/*
mkdir -p bin
javac -cp lib/jnanoid-2.0.0.jar -d bin $(find . -name "*.java")
```

### ▶️ 2. Executar

```bash
java -cp "bin:lib/jnanoid-2.0.0.jar" App
```

---
## 📹 Vídeo de Demonstração

O vídeo de até **3 minutos** mostra:

* Cadastro e login
* Criação e listagem de listas
* Cadastro de produtos
* Associação de produtos às listas
* Busca de lista por código

 📽️ [Vídeo de Apresentação TP1](https://youtu.be/NpyloV69Be0)

 📽️ [Vídeo de Apresentação TP2](https://www.youtube.com/watch?si=7E3g5fbp5sFOmfiV&v=s9rRcJV1k44&feature=youtu.be)

* Cadastro de produtos
* Busca por palavras
* Ranking TF×IDF
* Adição a listas

 📽️ [Vídeo de Apresentação TP3](https://youtu.be/0hineopwbmE)

---

# 🧩 PresenteFácil 1.0 — TP3 (Índice Invertido)

Sistema de listas de presentes com cadastro de usuários, produtos e listas.  
Este trabalho implementa **busca por produtos via palavras (índice invertido com TF-IDF)** além da busca por **GTIN-13**, conforme o enunciado do TP3 da disciplina _Algoritmos e Estruturas de Dados III_.

> **Tecnologias:** Java 17+, I/O com `RandomAccessFile`, Hash Extensível, Árvore B+ e Índice Invertido (TF-IDF).


---

```markdown
## 📁 Estrutura do projeto

AEDs3TP3/
│
├── App.java → Classe principal do sistema (ponto de entrada)
│
├── base/ → Classes de suporte para manipulação de arquivos e estruturas base
│ ├── Arquivo.java
│ ├── ElementoLista.java
│ ├── HashExtensivel.java
│ ├── ListaInvertida.java
│ ├── ParIDEndereco.java
│ ├── Registro.java
│ ├── RegistroArvoreBMais.java
│ ├── RegistroHashExtensivel.java
│ └── TextUtils.java
│
├── controller/ → Controladores principais da aplicação (camada de lógica)
│ ├── ControleLista.java
│ ├── ControlePrincipal.java
│ ├── ControleProduto.java
│ └── ControleUsuario.java
│
├── dao/ → Acesso e persistência de dados (Data Access Object)
│ ├── ArquivoLista.java
│ ├── ArquivoListaProduto.java
│ ├── ArquivoProduto.java
│ ├── ArquivoUsuario.java
│ └── IndiceInvertidoProdutos.java
│
├── index/ → Estruturas auxiliares de indexação
│ ├── ArvoreBMais.java
│ ├── ParEmailID.java
│ ├── ParIntInt.java
│ └── arvore.db
│
├── model/ → Classes de modelo (entidades principais)
│ ├── Lista.java
│ ├── ListaProduto.java
│ ├── Produto.java
│ └── Usuario.java
│
├── view/ → Interface de interação textual com o usuário
│ ├── VisaoLista.java
│ ├── VisaoPrincipal.java
│ ├── VisaoProduto.java
│ └── VisaoUsuario.java
│
├── lib/ → Dependências externas
│ └── jnanoid-2.0.0.jar
│
└── dados/                               → Arquivos de dados e índices persistidos (.db)
├── usuarios.db                          → Tabela principal de usuários
├── usuarios.email.idx*{d,c}.db          → Índice hash extensível de e-mails
├── listas.db                            → Tabela principal de listas de presentes
├── listas.usuario.idx.db                → Índice árvore B+ (usuário → lista)
├── produtos.db                          → Tabela principal de produtos
├── listaproduto.db                      → Relacionamento N:N (lista ↔ produto)
├── listaproduto.idxLista.db             → Índice B+ (idLista → idListaProduto)
├── listaproduto.idxProduto.db           → Índice B+ (idProduto → idListaProduto)
├── dicionario.listainv.db               → Dicionário do índice invertido
├── dicionario.listainv.hash*{c,d}.db    → Arquivos auxiliares do índice invertido
├── lista.listainv.db                    → Blocos contendo as listas invertidas
├── lista.listainv.hash*{c,d}.db         → Arquivos auxiliares de listas invertidas
├── blocos.listainv.db                   → Dados normalizados das palavras indexadas
└── produtos.db.hash*{c,d}.db            → Índices hash de produtos
```

---

## 🔎 Índice Invertido — TF × IDF

### Etapas de indexação

1. **Normalização:** palavras minúsculas, sem acento/pontuação (`TextUtils.normalize`).
2. **Stop Words:** remoção de artigos e preposições (`TextUtils.STOP_WORDS`).
3. **TF:** frequência do termo dentro do nome do produto.
4. **IDF:** `log(N/df) + 1`, onde  
   `N` = total de produtos e `df` = produtos que contêm o termo.
5. **Ranking:** soma de `TF × IDF` por produto durante a busca.

### Exemplo

| ID  | Nome do Produto                                 |
| --- | ----------------------------------------------- |
| 1   | Barbeador elétrico Philips bivolt               |
| 2   | Copo Stanley verde                              |
| 3   | Copo de vinho tinto                             |
| 4   | Liquidificador elétrico Oster com copo de vidro |

Consulta: **“copo vinho”**  
Resultado ordenado por relevância (TF×IDF): **[3, 2, 4]**

---

## ⚙️ Como compilar e executar

> Requisitos: **Java 17+**

### Linux/macOS/visual studio

```bash
mkdir -p bin
javac -cp lib/jnanoid-2.0.0.jar -d bin $(find . -name "*.java")
java  -cp "bin:lib/jnanoid-2.0.0.jar" App
```

### Windows (PowerShell)

```powershell
mkdir bin
javac -cp "lib\jnanoid-2.0.0.jar" -d bin (Get-ChildItem -Recurse -Filter *.java | % FullName)
java  -cp "bin;lib\jnanoid-2.0.0.jar" App
```

---

## ▶️ Passos de uso

1. **Login / Cadastro de usuário**
2. **Menu → Produtos**

   - Cadastrar novo produto (nome + descrição + GTIN-13)
   - Buscar por GTIN-13
   - Buscar por palavras (TF-IDF)

3. **Menu → Minhas Listas**

   - Criar lista e adicionar produtos por GTIN ou palavra-chave

### Exemplos de nomes

- Barbeador elétrico Philips bivolt
- Copo Stanley verde
- Copo de vinho tinto
- Liquidificador elétrico Oster com copo de vidro

### Exemplos de busca

- `copo vinho` → ordena por relevância
- `elétrico` → barbeador e liquidificador
- `philips` → barbeador
- `oster` → liquidificador

## ✅ Checklist exigido

| Pergunta                                                                   | Sim/Não | Justificativa                               |
| -------------------------------------------------------------------------- | ------- | ------------------------------------------- |
| O índice invertido usa `ListaInvertida`?                                   | ✅ Sim  | Implementado em `/base/ListaInvertida.java` |
| É possível buscar produtos por palavras no menu de produtos?               | ✅ Sim  | Opção “Buscar por palavras”                 |
| É possível buscar produtos por palavras ao acrescentar produtos às listas? | ✅ SIM  | Integração com `ControleLista`              |
| O trabalho compila corretamente?                                           | ✅ Sim  | Testado via linha de comando                |
| O trabalho funciona sem erros de execução?                                 | ✅ Sim  | Operações de CRUD e busca testadas          |
| O trabalho é original do grupo?                                            | ✅ Sim  | Código escrito e documentado pelo grupo     |

---

## 🧪 Detalhes técnicos

- **Persistência:** arquivos binários `.db` com exclusão lógica.
- **Reindexação:** nomes alterados atualizam as entradas no índice invertido.
- **Árvore B+:** relaciona `Lista` ↔ `Produto`.
- **Hash Extensível:** índices diretos de entidades.
- **TextUtils:** remove acentos e filtra stop words.


---

## 🔎 Índice Invertido — TF × IDF

### Etapas de indexação

1. **Normalização:** palavras minúsculas, sem acento/pontuação (`TextUtils.normalize`).
2. **Stop Words:** remoção de artigos e preposições (`TextUtils.STOP_WORDS`).
3. **TF:** frequência do termo dentro do nome do produto.
4. **IDF:** `log(N/df) + 1`, onde
   `N` = total de produtos e `df` = produtos que contêm o termo.
5. **Ranking:** soma de `TF × IDF` por produto durante a busca.

### Exemplo

| ID  | Nome do Produto                                 |
| --- | ----------------------------------------------- |
| 1   | Barbeador elétrico Philips bivolt               |
| 2   | Copo Stanley verde                              |
| 3   | Copo de vinho tinto                             |
| 4   | Liquidificador elétrico Oster com copo de vidro |

Consulta: **“copo vinho”**
Resultado ordenado por relevância (TF×IDF): **[3, 2, 4]**

---

## ⚙️ Como compilar e executar

> Requisitos: **Java 17+**

### Linux/macOS/visual studio qualquer plataforma

```bash
mkdir -p bin
javac -cp lib/jnanoid-2.0.0.jar -d bin $(find . -name "*.java")
java  -cp "bin:lib/jnanoid-2.0.0.jar" App

```

---

## 🪪 Licença

Este projeto é de uso acadêmico — PUC Minas / ICEI.
Sugestão: [MIT License](https://opensource.org/licenses/MIT)

---
