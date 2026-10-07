# coopdxforum

## PostgreSQL

The local PostgreSQL database runs in Docker Compose. From the repository root, start it with:

```powershell
docker compose -f .\backend\database\compose.yaml up -d
```

The initial schema is in [`backend/database/schema.sql`](backend/database/schema.sql); Docker
imports it automatically when it initializes an empty database volume. PostgreSQL is published
on host port `15432`. The local development credentials are database `coopdxforum`, user `Romza`,
and password `Romza123`. These credentials are for local development only.

Adminer is available at <http://localhost:8887>. Use server `postgres`, port `5432`, database
`coopdxforum`, user `Romza`, and password `Romza123` to sign in. ChartDB is available at
<http://localhost:8888>; use host `postgres` and port `5432` from its container.

From a database client running on the host, connect with host `localhost`, port `15432`,
database `coopdxforum`, user `Romza`, and password `Romza123`.

```powershell
docker compose -f .\backend\database\compose.yaml exec postgres sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"'
```

In `psql`, verify the imported tables with `\dt` and inspect a table with `\d "Users"`.
Table names are case-sensitive because the schema uses quoted names.

The schema initialization script only runs when PostgreSQL creates a new, empty data volume.
Editing `schema.sql` later will not update an existing database; use migrations for subsequent
schema changes. The named `postgres_data` volume persists across container restarts. Removing
that volume deletes the database contents.

## Spring Boot backend

The Maven project and Maven Wrapper are in `backend/`. Java 21 is required; Maven is downloaded
automatically by the wrapper on first use.

```powershell
cd .\backend
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Use `postgres:5432` as the database host/port when the backend runs in the same Compose network,
or `localhost:15432` when it runs directly on the host.

## Bruno API collection

The OpenAPI collection is in [`bruno/coopdxforum-openapi.yaml`](bruno/coopdxforum-openapi.yaml).
Import this YAML file into Bruno to generate the requests for the API at `http://localhost:8080`.
