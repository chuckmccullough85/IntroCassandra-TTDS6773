# Overview
In this exercise, you will learn about the Cassandra Write Path

---

## Steps
1. Using a file explorer or terminal, look at the cassandra data directory
   1. *(cassandra/data)*
   2. You will find several subdirectories, including *commitlog*, *hints*, and *data*
   3. *data/data* will contain subdirectories for each keystore
2. Now, from a command terminal, execute
```cassandra-stress write no-warmup n=75000 -port native=9042 -rate threads=1```
3. Notice the changes in the commit log and the data subdirectories
4. nodetool cfstats keyspace1.standard1

The stress tool created the keyspace *keyspace1* and the table *standard1*.  The stats on the table show the writes to the keyspace.  Namely, notice

```
Memtable cell count: 75000
Memtable data size: 20925000
```

5. Now run ```nodetool flush```
6. Notice the memtable is now 0

Drill down into the *data* folder and then into one of our movie keyspaces.  The subfolders contain partitions then SSTables

We will look at CFSTATS more later

---

### Compaction

1. From a command line, run ```nodetool compactionhistory```
   1. The output will be large. Scroll to the top for the most recent compaction
2. In a different terminal, run cqlsh and delete one or more years of movies from *collections_lab.movies*
3. Run ```nodetool compact collections_lab```
4. Look at the compaction history again.  Notice the ratio (around 1:20) or 5% of the SSTables were compacted.  That's about right since there's about 20 years of movies in the table.

*Check out all the nodetool commands by running **nodetool help***



