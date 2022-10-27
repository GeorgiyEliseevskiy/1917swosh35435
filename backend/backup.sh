#!/bin/bash

USERNAME=postgres
PASSWORD=4167004
DATABASE=swooshDB
export PGPASSWORD="$PASSWORD"
docker exec -i db pg_dump --username=$USERNAME --dbname=$DATABASE > create.sql
unset PGPASSWORD
echo "Pull Complete"