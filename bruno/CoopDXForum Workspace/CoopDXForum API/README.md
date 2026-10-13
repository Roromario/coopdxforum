# CoopDXForum API - Bruno

## Import

In Bruno, choose **Workspace > Import Workspace > From ZIP File** and select
`CoopDXForum API.zip`. Choose a destination folder and import. The ZIP includes
`workspace.yml` and the collection manifest. Select the `local` environment before sending
requests; it targets `http://localhost:8080`.

Start PostgreSQL and the Spring Boot backend before sending requests:

```powershell
docker compose -f .\backend\database\compose.yaml up -d
cd .\backend
.\mvnw.cmd spring-boot:run
```

## Suggested test order

1. Create a host user; its response stores `userId`.
2. Create an invited user; its response stores `participantUserId`.
3. Create a game hosted by `userId`; the response stores `gameId`.
4. Create an event using `gameId`; the response stores `eventId`.
5. Create an invitation for `participantUserId` and `eventId`; the response stores `invitationId`.
6. Run **List upcoming invitations by user** to check the invited player can find upcoming games.
7. Run **My games as invited user** to check `joined: false`.
8. Run **Mark invitation as joined** while the game is upcoming; it should return `409 Conflict`.
9. Run **End game**, then **Mark invitation as joined** again; it should still return `409 Conflict`.
10. Run **Update game** to move its start into the past and clear its end date.
11. Rerun **Mark invitation as joined**; it should succeed. Rerun **My games as invited user**
    and **Get invitation** to check `joined: true`.
12. Run **Get game code** to check the game-code route.
13. Delete test data in reverse dependency order: invitation, event, game, invited user, host user.

The create requests save returned IDs as Bruno runtime variables for subsequent requests.
