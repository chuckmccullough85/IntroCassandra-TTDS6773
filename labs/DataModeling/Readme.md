# Overview
In this lab, we will design a database of movies to support the required queries.

## Setup
In the folder *studentwork/DataModeling*, csv files contain sample movie data for recent movies.
* movies.csv - contains id, year, title, genre, rating
* actors.csv - contains id, actor name
* movie_actor.csv - contains movie id, actor id

Note - without writing applicaton code (Java, C#, Python, etc) we are limited in 
how we can apply this data to the database.  In a real-world scenario, we would design the tables
and have the application developers populate the database.

**Create a new keyspace for this lab!**
```cql
CREATE KEYSPACE data_model_lab WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':'1'};
```

## Requirements
The required application needs to query the database for the following information:

* Find a movie by name and get the details of the movie along with the cast
* Find an actor by name and show the movies that actor has appeared in
* Find movies from a particular range of years in a given genre

Choose the partitioning key carefully.  Remember, that must be the first item in the WHERE clause.

### Find movie by name
This might be the easiest requirement.  We can simply create a table where the movie name is the partition key and id can be used as a cluster key to make a unique primary key.  We can then query for movie details.  A separate query will have to be made to find actors.

Let's create a table that matches the movies.csv with title as the partition key and id as a clustering key:

```cql
CREATE TABLE movies ( id int, year int, title text, genre text, rating text, PRIMARY KEY (title, id));
```

We can import data from *movies.csv* with the COPY command:

```cql
COPY movies (id, year, title, genre, rating) FROM 'movies.csv';
```

Try different queries to view your favorite movies!

#### Cast Members
We are provided with a CSV that contains movie/actor pairs.  We also have a CSV that contains actor name and id pairs.

We can define tables, import the data, and use that to find actor's names.

```cql
CREATE TABLE movie_actor (movie_id int, actor_id int, PRIMARY KEY (movie_id, actor_id));

COPY movie_actor (movie_id, actor_id) FROM 'movie_actor.csv';

CREATE TABLE actor_by_id (
    id int PRIMARY KEY,
    name text
);

COPY actor_by_id (id, name) from 'actors.csv';
```

---

It takes several queries, but we can eventually answer the question of a movie and cast:

```select * from movies where title='Ride Along';```
```
 title      | id    | genre                 | rating | year
------------+-------+-----------------------+--------+------
 Ride Along | 21876 | Action, Comedy, Crime |  PG-13 | 2014
```
```select * from movie_actor where movie_id = 21876;```

```
 movie_id | actor_id
----------+----------
    21876 |      554
    21876 |     6270
    21876 |    11572
    21876 |    12045
    21876 |    14720
    21876 |    15838
    21876 |    48019
    21876 |    61441
    21876 |    73327
    21876 |    80985
    21876 |    94331
    21876 |   113392
```

```select name from actor_by_id where id = 554;```

```
 name
----------
 Ice Cube
```


**It will take a bunch of queries to get the information we want!**


### Find Actor by Name
In order to search for actors by name, we will either need to ALLOW FILTERING on the actors table (NO) or create another table partitioned by actor name.

In order to find that actor's movies, we will need an association table partitioned by the actor's id, and a movie table partitioned on the movie id.

```cql
CREATE TABLE actor_by_name (id int, name text, PRIMARY KEY (name, id));

COPY actor_by_name (id, name) FROM 'actors.csv';

CREATE TABLE actor_movie (actor_id int, movie_id int, PRIMARY KEY (actor_id, movie_id));

COPY actor_movie (actor_id, movie_id) from 'movie_actor.csv';

CREATE TABLE movie_by_id (id int PRIMARY KEY, year int, title text, genre text, rating text, year int);
COPY movie_by_id (id, title, genre, rating) from 'movies.csv';

```

Now, we can find an actor by name, then find their list of movie ids, then find the movie (whew!).

```cql
select * from actor_by_name where name='Kevin Hart';

 name       | id
------------+-------
 Kevin Hart | 14720

select * from actor_movie where actor_id = 14720;

 actor_id | movie_id
----------+----------
    14720 |     2317
    14720 |     2319
    14720 |     3264
    14720 |     3268
    14720 |     3280
    14720 |     3724
    14720 |     3726
    14720 |     3727
    14720 |     3735
    14720 |     3744
    14720 |     3821
    //...

select * from movie_by_id where id=4224;

 id   | genre              | rating | title                              | year
------+--------------------+--------+------------------------------------+------
 4224 | Documentary, Music |    N/A | Mogwai: The Recording of Mr. Beast | 2006

select * from movie_by_id where id=4694;

 id   | genre                 | rating    | title         | year
------+-----------------------+-----------+---------------+------
 4694 | Action, Drama, Horror | NOT RATED | A Bloody Aria | 2006

```

---

### Conclusions

We had to create a duplication of all the data in multiple tables to support the various queries.  

**Issues**
* We used a *table-driven* approach, polluted by relational models
* We assumed that we could *join*, resulting in many queries

We will address these problems in the next few topics!