**📌 HotelBooking - Documentação**

**📖 Visão Geral**

Este projeto é um sistema de reserva de quartos de hotel desenvolvido em Scala com Akka HTTP. Ele gerencia o inventário de quartos e reservas, impedindo overbooking e respeitando um intervalo de 4 horas para limpeza dos quartos.

**🛠 Tecnologias Utilizadas** 

🚀 Linguagem: Scala 2.13

🌍 Framework HTTP: Akka HTTP

🗄️ Banco de Dados: PostgreSQL

🔗 ORM: Slick

⚙️ Gerenciador de Build: sbt

**📌 Estrutura do Projeto**

Organizei o projeto em camadas separadas para melhor manutenção e modularidade.

**📌 Design Patterns Utilizados**

**🏛️ Repository Pattern**

Foi utilizado o Repository Pattern para desacoplar a lógica de persistência e facilitar futuras mudanças no banco de dados.

**🎯 Service Layer Pattern**

Foi criada uma camada de serviços para organizar as regras de negócio separadamente da API e do banco de dados.

**🏗️ Dependency Injection**

Foram injetadas dependências nos serviços, permitindo que os repositórios fossem substituídos em testes ou em configurações específicas.

**📌 Decisões Estratégicas**

**🗄️ Banco de Dados**

Eu utilizei PostgreSQL por sua confiabilidade e suporte para transações complexas. Utilizei Slick para facilitar a comunicação com o banco.

**⏳ Concorrência e Overbooking**

Foram implementadas verificações antes da inserção no banco para impedir reservas duplicadas no mesmo horário.

**⚠️ Tratamento de Erros**

As respostas da API foram padronizadas, também foram adicionadas mensagens de erro claras para melhorar a usabilidade.

**📌 Endpoints da API**

🏨 Quartos

# Adicionar um novo quarto
POST http://localhost:8080/rooms
Content-Type: application/json
{
  "name": "Nome do Quarto"
}

# Listar todos os quartos
GET http://localhost:8080/rooms

# Remover um quarto
DELETE http://localhost:8080/rooms/{id}

🛏️ Reservas

# Criar uma nova reserva
POST http://localhost:8080/reservations
Content-Type: application/json
{
  "roomId": 1,
  "guestName": "Nome do Cliente",
  "startTime": "2024-02-19T14:00:00",
  "endTime": "2024-02-19T16:00:00"
}

# Obter a ocupação do hotel por data
GET http://localhost:8080/reservations/2024-02-19T00:00:00

# 📌 Conclusão

Eu desenvolvi este projeto utilizando boas práticas de arquitetura, design patterns e segurança, garantindo gestão eficiente de reservas, controle de concorrência e API bem estruturada.
