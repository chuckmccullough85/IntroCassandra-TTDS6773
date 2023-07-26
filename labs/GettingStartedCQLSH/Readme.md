# Overview
In this lab, we will launch a CQLSH session, define a simple keyspace, table, and query data.

## Steps

1. Start Cassandra if not already running
2. Start CQLSH
3. Create a new keyspace named people with a replication factor of 1
4. Define a new table named *people*
5. The table should have these fields:
* id
* name
* birthdate
* state
6. Insert some rows
7. Query the table to verify
