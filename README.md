# coopdxforum

## PostgreSQL

The local PostgreSQL database runs in Docker Compose. The initial schema is in
[`backend/database/schema.sql`](backend/database/schema.sql); Docker imports it automatically
when it initializes an empty database volume.

```powershell
Copy-Item .env.example .env
docker compose up -d
```

Edit `.env` and replace the example password before starting the database. Do not commit
`.env`; it is ignored by Git. The database is only published on this computer's localhost
port `5432`.

Connect to PostgreSQL from a database client with host `localhost`, port `5432`, and the
database/user/password values from `.env`. To open the PostgreSQL shell inside the container:

```powershell
docker compose exec db sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"'
```

In `psql`, verify the imported tables with `\dt` and inspect a table with `\d "Users"`.
Table names are case-sensitive because the schema uses quoted names.

The schema initialization script only runs when PostgreSQL creates a new, empty data volume.
Editing `schema.sql` later will not update an existing database; use migrations for subsequent
schema changes. The named `postgres_data` volume persists across container restarts. Removing
that volume deletes the database contents.

The repository does not yet contain a backend database client or connection settings. A
backend running in Compose should connect to host `db` and port `5432` (not `localhost`);
a backend running directly on the host should connect to `localhost:5432`.
