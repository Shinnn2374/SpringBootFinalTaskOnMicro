#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE user_db;
    CREATE DATABASE task_db;

    CREATE USER user_service WITH PASSWORD 'password';
    CREATE USER task_service WITH PASSWORD 'password';

    GRANT ALL PRIVILEGES ON DATABASE user_db TO user_service;
    GRANT ALL PRIVILEGES ON DATABASE task_db TO task_service;

    \c user_db;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO user_service;

    \c task_db;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO task_service;
EOSQL