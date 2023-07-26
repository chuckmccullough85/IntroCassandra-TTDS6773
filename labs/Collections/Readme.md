# Overview
In this lab, we will use *indexes* and *materialzed views* to improve the data model

## Steps
1. Create a new keyspace named collections_lab
```cql
CREATE KEYSPACE collections_lab WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}
```
2. *use* the keyspace
3. Create a table named *movies*
   * id
   * year
   * title
   * genre
   * rating
   * **cast** use a set to hold the names of the cast members
   * Partition by year and cluster by title and id
4. Copy the file *movies.csv* into the table 
   * inspect the csv to determine the column names and order
5. Create an index on *movies* so that we can also search for a specific cast member (actor);
6. Find the movies your favorite actor has been in
7. Find your favorite movies 


:::
```cql
create table movies (id int, year int, title text, genre text, rating text, cast set<text>, primary key (year, title, id));

create index on movies (cast);
select * from movies where cast contains 'Anthony Hopkins';

```