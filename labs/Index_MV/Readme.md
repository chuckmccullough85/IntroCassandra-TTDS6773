# Overview
In this lab, we will create indexes and materialized views on the the tables from the last lab

---

## Steps

In the previous lab, we created 2 association tables: movie_actor and actor_movie.  The only difference was the partition key vs cluster key.  By using an index, we can eliminate one of the tables.

1. Drop table *actor_move*
2. Create an index on *movie_actor* with *actor_id*
3. Verify that we can find movies for an actor and actors for a movie using *movie_actor* table

We also created a duplicate table of *movies* called *movie_by_id* so that we could find movies from the id.  Instead, we could create a materialized view making id the partition key.

1. Drop table *movie_by_id*
2. Create a materialized view of *movies* making id the partition key

In both cases, we added lookup capabilities for an existing table.  In one case, we added an index and the other a materialized view.  Either would have worked in either situation.  Explain the pros and cons of the two approaches on our scenario.

