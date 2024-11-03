# Finanças

## Descrição do Projeto
O **Finanças** é uma aplicação web desenvolvida em Java com o framework Spring Boot, projetada para ajudar os usuários a monitorar e controlar suas despesas mensais. A aplicação permite categorizar gastos, exibir resumos mensais e calcular o saldo restante com base em um salário configurado pelo usuário.

## Estrutura do Projeto

Abaixo está uma visão geral da estrutura do projeto, organizada em camadas para melhor manutenção e escalabilidade.

### Diretório `src/main/java/com/financas`

- **controllers**: Responsável por gerenciar as requisições HTTP e direcionar o fluxo de dados entre a interface do usuário e os serviços.
   - **`CategoriaController`**: Controla as operações e visualizações relacionadas às categorias de despesas.
   - **`DespesaController`**: Controla operações específicas para a entidade `Despesa`.
   - **`MesController`**: Gerencia a visualização de despesas e categorias agrupadas por mês.

- **data**: Contém a configuração de conexão com o banco de dados.
   - **`DataConfiguration`**: Classe de configuração do banco de dados MySQL, onde são definidos a URL de conexão, o nome de usuário e a senha.

- **models**: Define as entidades principais do sistema, representando as tabelas do banco de dados.
   - **`Categoria`**: Representa uma categoria de despesa, como Cartão de Crédito, Moradia ou Saúde.
   - **`Despesa`**: Representa uma despesa específica, associada a uma categoria e a um mês.
   - **`Mes`**: Representa cada mês do ano, facilitando o controle dos totais de despesas e do orçamento.

- **repositories**: Contém as interfaces de repositório, responsáveis pela comunicação com o banco de dados, utilizando a API JPA do Spring.
   - **`CategoriaRepository`**: Repositório para operações de banco de dados com a entidade `Categoria`.
   - **`DespesaRepository`**: Repositório para operações de banco de dados com a entidade `Despesa`.
   - **`MesRepository`**: Repositório para operações de banco de dados com a entidade `Mes`.

- **services**: Camada de serviço, onde ocorre a lógica de negócios.
   - **`CategoriaService`**: Lógica de negócios para gerenciar as categorias.
   - **`DespesaService`**: Lógica de negócios para gerenciar as despesas.
   - **`MesService`**: Lógica de negócios para calcular e manipular os dados financeiros dos meses.

- **FinancasApplication**: Classe principal do Spring Boot que inicializa a aplicação.

### Diretório `src/main/resources`

- **static**: Arquivos estáticos, incluindo estilos e scripts.
   - **`css/style.css`**: Arquivo de estilos CSS para estilização das páginas HTML.
   - **`script/script.js`**: Arquivo JavaScript que fornece funcionalidades de navegação entre meses e categorias.

- **templates**: Páginas HTML Thymeleaf que compõem a interface da aplicação.
   - **`index.html`**: Página inicial, que lista todos os meses com links para visualização detalhada.
   - **`mes.html`**: Página para visualização dos gastos em um mês específico, listando as categorias e o total de despesas.
   - **`categoria.html`**: Página para visualização das despesas em uma categoria específica dentro de um mês.

- **application.properties**: Arquivo de configuração do Spring Boot.

### Estrutura Completa do Projeto

```plaintext
src
├── main
│   ├── java
│   │   └── com.financas
│   │       ├── controllers          # Controladores para gerenciamento de rotas e lógica de apresentação
│   │       │   ├── CategoriaController
│   │       │   ├── DespesaController
│   │       │   └── MesController
│   │       ├── data                 # Configuração de conexão com o banco de dados
│   │       │   └── DataConfiguration
│   │       ├── models               # Entidades JPA que mapeiam as tabelas do banco de dados
│   │       │   ├── Categoria
│   │       │   ├── Despesa
│   │       │   └── Mes
│   │       ├── repositories         # Repositórios para acesso a dados
│   │       │   ├── CategoriaRepository
│   │       │   ├── DespesaRepository
│   │       │   └── MesRepository
│   │       ├── services             # Lógica de negócios e serviços
│   │       │   ├── CategoriaService
│   │       │   ├── DespesaService
│   │       │   └── MesService
│   │       └── FinancasApplication  # Classe principal para inicialização do Spring Boot
│   ├── resources
│   │   ├── static                   # Arquivos estáticos como CSS e JavaScript
│   │   │   ├── css
│   │   │   │   └── style.css        # Arquivo CSS de estilização
│   │   │   └── script
│   │   │       └── script.js        # JavaScript para navegação e interação
│   │   ├── templates                # Templates Thymeleaf para renderização no frontend
│   │   │   ├── index.html           # Página inicial com a lista de meses
│   │   │   ├── mes.html             # Página de visualização das despesas por mês
│   │   │   └── categoria.html       # Página de visualização das despesas por categoria
│   │   └── application.properties   # Configurações do Spring Boot
│   ├── test                         # Diretório para testes
└── README.md                        # Documentação do projeto

```

## Funcionalidades

1. **Visualização por Mês**: Exibe todas as categorias de despesas e os respectivos totais de gastos para um mês selecionado.
2. **Visualização por Categoria**: Permite a visualização detalhada das despesas em uma categoria específica, filtradas por mês.
3. **Resumo Financeiro**: Exibe um resumo do salário, total de gastos e valor restante para o mês.
4. **Navegação entre Meses e Categorias**: Permite ao usuário navegar facilmente entre meses e categorias usando botões de navegação intuitivos.
5. **Configuração de Salário e Categoria**: Usuários podem configurar o salário e adicionar novas categorias.

## Configuração do Banco de Dados

A aplicação utiliza MySQL como banco de dados. A configuração está no arquivo `DataConfiguration` na pasta `data`. Ajuste os parâmetros conforme necessário.

### Exemplo de Configuração

```java
// DataConfiguration.java
URL: jdbc:mysql://localhost:[porta]/[banco]?useTimezone=true&serverTimezone=UTC
Username: 
Password: 
```

Certifique-se de que o MySQL esteja instalado e o banco de dados esteja configurado corretamente antes de iniciar a aplicação.

## Pré-requisitos

- **Java 17** ou superior
- **MySQL Server 8.0**
- **Spring Boot**
- **Maven** (para gerenciamento de dependências)

## Passo a Passo para Execução

1. **Clone o Repositório**

   ```bash
   git clone https://github.com/MouraYuri93/financas.git
   ```

2. **Configure o Banco de Dados**

   Crie um banco de dados no MySQL e atualize as configurações em `DataConfiguration.java` com as credenciais do seu ambiente.

3. **Instale as Dependências e Inicie o Projeto**

   Navegue até a pasta do projeto e execute o comando:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse a Aplicação**

   Abra seu navegador e vá para `http://localhost:8080`.

## Navegação

### Estrutura de Navegação da Interface

- **Tela Inicial (`index.html`)**: Lista todos os meses do ano com um resumo dos valores totais de despesas.
- **Página do Mês (`mes.html`)**: Exibe as categorias de despesas para o mês selecionado e o total geral de gastos.
- **Página da Categoria (`categoria.html`)**: Exibe as despesas detalhadas em uma categoria específica para o mês selecionado.

### JavaScript de Navegação

O arquivo `script.js` contém funções que permitem:
- **Navegar entre meses**: Botões de "Mês Anterior" e "Próximo Mês".
- **Navegar entre categorias**: Botões de "Categoria Anterior" e "Próxima Categoria".
- **Botão de Voltar**: Retorna o usuário para a página anterior ou para a página principal conforme o contexto.

## Estrutura do Banco de Dados

### Tabelas Principais

1. **Categoria**: Define as diferentes categorias de despesas, como Cartão de Crédito, Educação, etc.
2. **Despesa**: Registra despesas específicas, vinculadas a uma categoria e a um mês.
3. **Mes**: Registra informações sobre cada mês, incluindo o total de despesas e o salário configurado.

## Contribuindo

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões, sinta-se à vontade para abrir

uma issue ou enviar um pull request.

## Licença

Este projeto está sob a Licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.