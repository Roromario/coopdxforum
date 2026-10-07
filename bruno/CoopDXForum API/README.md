# CoopDXForum API - Bruno

## Import

Extract `CoopDXForum API.zip`, then in Bruno choose **Open Collection** and select the
extracted `CoopDXForum API` folder. Select the `local` environment to target
`http://localhost:8080`.

Start PostgreSQL and the Spring Boot backend before sending requests:

```powershell
docker compose -f .\backend\database\compose.yaml up -d
cd .\backend
.\mvnw.cmd spring-boot:run
```

## Suggested test order

1. Create a user; its response stores `userId`.
2. Create a game; it uses `userId` and stores `gameId`.
3. Create an event; it uses `gameId` and stores `eventId`.
4. Create an invitation; it uses `userId` and `eventId`, and stores `invitationId`.
5. Run the related Get, List, and Update requests.
6. Delete test data in reverse dependency order: invitation, event, game, user.

The create requests save returned IDs as Bruno runtime variables for subsequent requests.
