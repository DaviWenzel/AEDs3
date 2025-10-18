# 🎁 PresenteFácil 1.0 — TP1 (Relacionamento 1:N)

Sistema de linha de comando para **gestão de listas de presentes**.  
Cada **Usuário** pode criar **N Listas**, e cada Lista possui um **código compartilhável (NanoID)** para consulta por terceiros.

> 📽️[ Vídeo de Apresentação TP1] (https://youtu.be/NpyloV69Be0)

---

## 🛠️ Como Compilar e Executar

### ✅ Requisito
- **JDK 17+ instalado**

### 🔄 1. Limpar e Compilar

```bash
rm -rf bin dados/*
mkdir -p bin
javac -cp lib/jnanoid-2.0.0.jar -d bin $(find . -name "*.java")
````

### ▶️ 2. Executar

```bash
java -cp "bin:lib/jnanoid-2.0.0.jar" App
```

---

## 🧱 Arquitetura

* **Linguagem:** Java
* **Persistência:** Arquivos binários + índices
* **Padrão de Projeto:** MVC

  * `model`, `dao`, `controller`, `view`

---

## 📂 Funcionalidades

* **CRUD genérico**:

  * Arquivo de dados com:

    * Lápide
    * Tamanho
    * Conteúdo serializado

* **Índices**:

  * **Direto**: Hash Extensível (`id → endereço`)
  * **Secundário (email)**: Hash Extensível (`email → id`)
  * **Relação 1:N (Usuário → Listas)**: Árvore B+ com chaves `(idUsuario; idLista)`

* **Código Compartilhável de Lista**:

  * Geração via [NanoID](https://github.com/aventrix/jnanoid)

---

## 📌 Observações

* **Este repositório implementa o TP1**:

  * Foco em **usuários** e **listas**
  * **Produtos ainda não incluídos**

---

## 📄 Licença

Projeto acadêmico — uso livre para **fins educacionais**.
