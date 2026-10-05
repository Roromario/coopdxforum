# coopdxforum

## PostgreSQL

The local PostgreSQL database runs in Docker Compose. The initial schema is in
[`backend/database/schema.sql`](backend/database/schema.sql); Docker imports it automatically
when it initializes an empty database volume.

```powershell
docker compose up -d
```

The local development credentials match the class example: database `db`, user `username`,
password `password`. These fixed credentials are for local development only; do not use them
for a public or production deployment. PostgreSQL is available on the host at port `15432`.

Adminer is available at <http://localhost:8887>. Use server `postgres`, port `5432`, and the
database `db`, user `username`, and password `password` to sign in. ChartDB is available at
<http://localhost:8888>; use host `postgres` and port `5432` to connect to the database from it.

From a database client running on the host, connect with host `localhost`, port `15432`,
database `db`, user `username`, and password `password`. To open the PostgreSQL shell inside the container:

```powershell
docker compose exec postgres sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"'
```

In `psql`, verify the imported tables with `\dt` and inspect a table with `\d "Users"`.
Table names are case-sensitive because the schema uses quoted names.

The schema initialization script only runs when PostgreSQL creates a new, empty data volume.
Editing `schema.sql` later will not update an existing database; use migrations for subsequent
schema changes. The named `postgres_data` volume persists across container restarts. Removing
that volume deletes the database contents.

The repository does not yet contain a backend database client or connection settings. A
backend running in Compose should connect to host `postgres` and port `5432` (not `localhost`);
a backend running directly on the host should connect to `localhost:15432`.
