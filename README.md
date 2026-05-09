# ironvault-notifications

Microserviço de notificações do ecossistema IronVault Payments.

## Responsabilidades

Serviço centralizado para envio de notificações transacionais via email (e futuramente WhatsApp/SMS), consumindo eventos dos demais serviços do ecossistema.

## Eventos Consumidos

| Serviço | Evento | Notificação |
|---|---|---|
| ironvault-auth | `UserRegistered` | Email de boas-vindas |
| ironvault-payments | `PaymentApproved` | Email de pagamento aprovado |
| ironvault-payments | `PaymentFailed` | Email de pagamento falhou |
| ironvault-payments | `PixGenerated` | Email com QR Code do PIX |
| ironvault-payments | `RefundProcessed` | Email de estorno processado |

## Stack

- Java 21
- Spring Boot 3.3.5
- PostgreSQL (Neon)
- Resend (email transacional)
- Maven

## Canais de Notificação

- ✅ Email (Resend)
- 🔜 WhatsApp
- 🔜 SMS

## Ecossistema IronVault

ironvault-auth ──────→ UserRegistered
ironvault-payments ──→ PaymentApproved
──→ PaymentFailed
──→ PixGenerated
──→ RefundProcessed
↓
ironvault-notifications
↓
Email (Resend) + WhatsApp (futuro)

## Serviços Relacionados

- [ironvault-payments](https://github.com/viniciuspascucci1993/ironvault-payments)
- [ironvault-auth](https://github.com/viniciuspascucci1993/ironvault-auth)

## Configuração

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

resend:
  api-key: ${RESEND_API_KEY}
  from: noreply@ironvaultpayments.com.br
```

## Variáveis de Ambiente

| Variável | Descrição |
|---|---|
| `DB_URL` | URL de conexão PostgreSQL |
| `DB_USERNAME` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_SECRET` | Secret JWT para validação |
| `RESEND_API_KEY` | API Key do Resend |