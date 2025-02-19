📌 HotelBooking - Documentação

📖 Visão Geral

Este projeto é um sistema de reserva de quartos de hotel desenvolvido em Scala com Akka HTTP. Ele gerencia o inventário de quartos e reservas, impedindo overbooking e respeitando um intervalo de 4 horas para limpeza dos quartos.

🛠 Tecnologias Utilizadas

Linguagem: Scala 2.13

Framework HTTP: Akka HTTP

Banco de Dados: PostgreSQL

ORM: Slick

Gerenciador de Build: sbt

📌 Estrutura do Projeto

Organizei o projeto em camadas separadas para melhor manutenção e modularidade:

HotelBooking/
│── src/main/scala/com/hotel/booking/
│   ├── api/                # Endpoints HTTP
│   │   ├── RoomRoutes.scala
│   │   ├── ReservationRoutes.scala
│   │
│   ├── domain/             # Modelos de Dados
│   │   ├── Room.scala
│   │   ├── Reservation.scala
│   │
│   ├── services/           # Regras de Negócio
│   │   ├── RoomService.scala
│   │   ├── ReservationService.scala
│   │
│   ├── repository/         # Persistência no Banco de Dados
│   │   ├── RoomRepository.scala
│   │   ├── ReservationRepository.scala
│   │
│   ├── infrastructure/     # Configurações e Inicialização do Servidor
│   │   ├── DatabaseConfig.scala
│   │   ├── Server.scala
│
│── build.sbt               # Configuração do sbt
│── README.md               # Documentação do Projeto

📌 Design Patterns Utilizados

Repository Pattern

Eu utilizei o Repository Pattern para desacoplar a lógica de persistência e facilitar futuras mudanças no banco de dados.

Service Layer Pattern

Eu criei uma camada de serviços para organizar as regras de negócio separadamente da API e do banco de dados.

Dependency Injection

Eu injetei dependências nos serviços, permitindo que os repositórios fossem substituídos em testes ou em configurações específicas.

📌 Decisões Estratégicas

Banco de Dados

Eu utilizei PostgreSQL por sua confiabilidade e suporte para transações complexas. Utilizei Slick para facilitar a comunicação com o banco.

Concorrência e Overbooking

Eu implementei verificações antes da inserção no banco para impedir reservas duplicadas no mesmo horário.

Tratamento de Erros

Eu padronizei as respostas da API e adicionei mensagens de erro claras para melhorar a usabilidade.

📌 Endpoints da API

Quartos

# Adicionar um novo quarto
POST http://localhost:8080/rooms
Content-Type: application/json
{
  "name": "Suite Deluxe"
}

# Listar todos os quartos
GET http://localhost:8080/rooms

# Remover um quarto
DELETE http://localhost:8080/rooms/{id}

Reservas

# Criar uma nova reserva
POST http://localhost:8080/reservations
Content-Type: application/json
{
  "roomId": 1,
  "guestName": "Andreza Leal",
  "startTime": "2024-02-19T14:00:00",
  "endTime": "2024-02-19T16:00:00"
}

# Obter a ocupação do hotel por data
GET http://localhost:8080/reservations/2024-02-19T00:00:00

📌 Conclusão

Eu desenvolvi este projeto utilizando boas práticas de arquitetura, design patterns e segurança, garantindo gestão eficiente de reservas, controle de concorrência e API bem estruturada.
