# Overview
In this lab, we will explore tuples and user defined types

---

## Tuples
A tuple is an arbitrary set of values.  If we wanted to associate the actor's name and Id in the cast colleciton, we can do that with a tuple.

### Steps
1. Create a new keyspace named *tuple_lab*
2. Define a new table named *movies* 
    * id
    * year
    * title
    * genre
    * rating
    * cast - a set of tuple of int, text
3. Import *movies_tup.csv* - note examine the contents of the csv first to get the correct column order
4. Define an index on cast
5. Find all movies with a specific actor
    * ie ```select year, title from movies where cast contains (2834, 'Bruce Willis');```

## User Defined Types
User defined types are similar to tuples, but are reusable since we have a type name to reference.

1. Create a type named *actor_type* with fields *name* and *id*
2. Define another table similar to *movies* named *movie_a* but use the actor type for the cast
3. Import data from *movies_act.csv* - note, review the file first
4. Define an index on cast
5. Query the table for movies and actors

