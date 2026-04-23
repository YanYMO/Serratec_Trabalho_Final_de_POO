# 📋 Sistema de Folha de Pagamento — POO em Java

> Projeto Final — Disciplina de Programação Orientada a Objetos

---

## 👥 Grupo 1

|   Nome   | Apresentação |
|----------|-----------------|
|   *Adriane*   | *Apresentação README* |
|   *Valois*   | *Banco de Dados* |
|   *Ana Luisa*   | *Classes principais* |
|   *Raquel*   | *Classes Interfaces* |
|   *Marcelo*   | *Classes Exception e Arquivo de dados* |
|   *Yan*   | *Classes DAOs e Classe Main* |


---

## 📌 Sobre o Projeto

Este sistema realiza o **cálculo de salário líquido** de funcionários de uma empresa, com suporte a dependentes, desconto de INSS e Imposto de Renda, persistência em banco de dados PostgreSQL e leitura/escrita de arquivos no formato CSV.

---

## 📐 Diagrama UML

> *<img width="3508" height="2480" alt="Image" src="https://github.com/user-attachments/assets/ac646676-f3a2-4a5b-ab4c-68ab6be417a9" />*
> 
> *<img width="3508" height="2480" alt="Image" src="https://github.com/user-attachments/assets/9e520be9-fd13-41f1-9743-9f8c5f56d169" />*


---

## 🗂️ Estrutura de Pacotes

```
br.com.FolhaPagamento
│
├── dao
│   ├── DependenteDAO.java          # Persistência de dependentes no banco
│   ├── FolhaPagamentoDAO.java      # Persistência e listagem da folha de pagamento
│   └── FuncionarioDAO.java         # Persistência de funcionários no banco
│
├── exception
│   ├── DependenteException.java    # Exceção customizada para dependentes
│   └── FuncionarioException.java   # Exceção customizada para funcionários
│
├── infra
│   └── ConnectionFactory.java      # Fábrica de conexão com o banco PostgreSQL
│
├── interface
│   ├── Impostos.java               # Interface: cálculo de INSS, IR e dependentes
│   └── Incluir.java                # Interface: adição de dependente e folha de pagamento
│
├── model
│   ├── enums
│   │   └── Parentesco.java         # Enum: FILHO, SOBRINHO, OUTROS
│   │
│   ├── Contracheque.java           # Representa os dados lidos do banco para exportação
│   ├── Dependente.java             # Herda de Pessoa
│   ├── FolhaPagamento.java         # Representa o contracheque gerado em memória
│   ├── Funcionario.java            # Herda de Pessoa, implementa Impostos e Incluir
│   └── Pessoa.java                 # Classe abstrata base
│
└── Main.java                       # Ponto de entrada — menu, leitura CSV e orquestração
```

---

## ⚙️ Funcionalidades

- ✅ Menu interativo via console com solicitação de arquivo de entrada e saída
- ✅ Leitura de arquivo CSV com dados de funcionários e dependentes
- ✅ Reprocessamento automático em caso de arquivo não encontrado (loop de retry)
- ✅ Cálculo automático de desconto de **INSS** com base em faixas salariais
- ✅ Cálculo automático de desconto de **Imposto de Renda (IR)** com dedução por dependentes
- ✅ Geração de arquivo CSV de saída com dados consultados diretamente do banco
- ✅ Persistência dos dados em banco de dados **PostgreSQL** (funcionários, dependentes e folha)
- ✅ Validação de **CPF duplicado** para funcionários e dependentes
- ✅ Validação de **idade dos dependentes** (máximo 18 anos)
- ✅ Conexão única com o banco reutilizada durante toda a execução
- ✅ Geração de IDs únicos com **UUID**

---

## 🧱 Conceitos de POO Utilizados

| Conceito | Onde foi aplicado |
|---|---|
| **Classe Abstrata** | `Pessoa` — base para `Funcionario` e `Dependente` |
| **Herança** | `Funcionario` e `Dependente` herdam de `Pessoa` |
| **Interfaces** | `Impostos` — cálculos tributários; `Incluir` — adição de dependentes e folha |
| **Encapsulamento** | Atributos privados com getters em todas as classes |
| **Modificadores de Acesso** | `private`, `protected`, `public` e `final` aplicados adequadamente |
| **Enum** | `Parentesco` — valores: `FILHO`, `SOBRINHO`, `OUTROS` |
| **Exceções Customizadas** | `DependenteException` e `FuncionarioException` |
| **LocalDate** | Datas de nascimento e pagamento tratadas com `LocalDate` |
| **Coleções** | `ArrayList` para listas de dependentes e folhas de pagamento |
| **Classes finais** | `Funcionario`, `Dependente`, `FolhaPagamento` e `Contracheque` |
| **Arquivos** | Leitura via `BufferedReader` e escrita via `BufferedWriter` |
| **Banco de Dados** | Persistência via JDBC com `PreparedStatement` e `ResultSet` |
| **Separação por pacotes** | Classes organizadas em `model`, `dao`, `exception`, `infra` e `interface` |

---

## 💰 Regras de Cálculo

### INSS

| Faixa Salarial | Alíquota | Dedução |
|---|---|---|
| Até R$ 1.518,00 | 7,5% | — |
| R$ 1.518,01 até R$ 2.793,88 | 9% | R$ 22,77 |
| R$ 2.793,89 até R$ 4.190,83 | 12% | R$ 109,60 |
| R$ 4.190,84 até R$ 8.157,41 | 14% | R$ 190,42 |
| Acima de R$ 8.157,41 | Fixo | R$ 951,62 |

### Imposto de Renda (IR)

> Base de cálculo: `Salário Bruto − Desconto INSS − (Nº Dependentes × R$ 189,59)`

| Base de Cálculo | Alíquota | Dedução |
|---|---|---|
| Até R$ 2.259,00 | Isento | — |
| R$ 2.259,01 até R$ 2.826,65 | 7,5% | R$ 169,44 |
| R$ 2.826,66 até R$ 3.751,05 | 15% | R$ 381,44 |
| R$ 3.751,06 até R$ 4.664,68 | 22,5% | R$ 662,77 |
| Acima de R$ 4.664,68 | 27,5% | R$ 896,00 |

### Salário Líquido

```
Salário Líquido = Salário Bruto − Desconto INSS − Desconto IR
```

---

## 📁 Formato dos Arquivos CSV

### Entrada

Cada bloco representa um funcionário seguido de seus dependentes, separados por **linha em branco**. O programa solicita o caminho completo do arquivo via console e tenta novamente caso o arquivo não seja encontrado.

```
Nome;CPF(11 dígitos);DataNascimento(YYYYMMDD);SalarioBruto
NomeDep;CPF;DataNascimento;PARENTESCO

Nome;CPF(11 dígitos);DataNascimento(YYYYMMDD);SalarioBruto
NomeDep;CPF;DataNascimento;PARENTESCO
NomeDep;CPF;DataNascimento;PARENTESCO
```

**Exemplo:**
```
Carlos Silva;12345678901;19850315;5000.00
Ana Silva;98765432100;20100820;FILHO

Maria Souza;11122233344;19900101;3200.00
```

### Saída

O programa solicita o nome do arquivo de saída (sem extensão) e gera automaticamente um `.csv` com os dados consultados diretamente do banco de dados.

```
Nome;CPF;DescontoINSS;DescontoIR;SalarioLiquido
```

**Exemplo:**
```
Carlos Silva;12345678901;551.62;423.80;4024.58
Maria Souza;11122233344;274.82;99.12;2826.06
```

---

## 🗄️ Banco de Dados

O sistema utiliza **PostgreSQL** com schema `matriz` e três tabelas:

- **`matriz.funcionario`** — dados do funcionário e descontos calculados
- **`matriz.dependente`** — dados do dependente vinculado ao seu funcionário
- **`matriz.folhaPagamento`** — registro do contracheque gerado por funcionário

A conexão é criada uma única vez na `Main` pela classe `ConnectionFactory` (pacote `infra`) e repassada para todos os DAOs, sendo encerrada ao final da execução.

Configurações padrão:

```
URL:      jdbc:postgresql://localhost:5432/contracheque
Schema:   matriz
Usuário:  postgres
```

> ⚠️ Atualize as credenciais em `br.com.FolhaPagamento.infra.ConnectionFactory` conforme seu ambiente local.

---

## ⚠️ Validações e Exceções

| Exceção | Pacote | Quando é lançada |
|---|---|---|
| `DependenteException` | `exception` | Dependente com **18 anos ou mais** |
| `DependenteException` | `exception` | Dependente com **CPF já cadastrado** para o mesmo funcionário |
| `FuncionarioException` | `exception` | Funcionário com **CPF já existente** na lista do arquivo |
| `RuntimeException` | — | Falha na inserção no banco (ex: CPF duplicado na tabela) |

> Erros de dependente e funcionário são tratados individualmente — um registro inválido **não interrompe** o processamento dos demais.

---

## ▶️ Como Executar

**Pré-requisitos:**
- Java 11 ou superior
- PostgreSQL instalado e rodando localmente
- Driver JDBC do PostgreSQL no classpath

**Passos:**

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   ```

2. Configure o banco de dados criando o schema `matriz` com as tabelas `funcionario`, `dependente` e `folhaPagamento`.

3. Atualize as credenciais em `br.com.FolhaPagamento.infra.ConnectionFactory`.

4. Compile e execute pela IDE de sua preferência (IntelliJ, Eclipse, etc.) ou via terminal:
   ```bash
   javac -cp .:postgresql.jar -d out $(find . -name "*.java")
   java -cp out:postgresql.jar br.com.FolhaPagamento.Main
   ```

5. Siga as instruções do menu:
   - Informe o **caminho do arquivo de entrada** (ex: `dados/funcionarios.csv`)
   - Informe o **nome do arquivo de saída** (ex: `folha_pagamento` → gera `folha_pagamento.csv`)

---

## 🔗 Repositório

> *https://github.com/YanYMO/Serratec_Trabalho_final_de_POO*

---

*Projeto desenvolvido para a disciplina de Programação Orientada a Objetos — Grupo 1*
