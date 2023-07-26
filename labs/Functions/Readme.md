# Overview
In this lab, we will alter the movies table to include a cast count.

## Steps
1. Define a new keyspace named *functions_lab*
2. Define a movies table (note - this is the same table from collections_lab):
```create table movies (id int, year int, title text, genre text, rating text, cast set<text>, primary key (year, title, id));```
3. Import the data from *movies_cast.csv*
4. The customer now wants to add an additional column named *cast_count*
5. Alter the table to include the additional column
6. Delete all the records in the table
7. Import the data from *movies_wcount.csv"

## Challenge
Answer these questions.  Define indexes or materialized views as needed.

* What is the largest cast in the database?
* What is the smallest cast?
* What is the average cast?
* Which movie(s) have the largest & smallest casts?


