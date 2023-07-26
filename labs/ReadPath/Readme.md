# Overview
In this exercise, we will explore the read path, in particular bloom filters

---

## Steps

1. Navigate to *(cassandra)/data/data/keyspace1*  
2. Search for files **\*Filter.db**
3. Make a note of the sizes
4. Open CQLSH
5. ```DESCRIBE keyspace keyspace1;```

Notice the setting ```AND bloom_filter_fp_chance = 0.01```. This means that there is a 1% chance the bloom filter provides a false positive.  Great!
Lets change that to a much lower value
6. ```ALTER TABLE keyspace1.standard1 WITH bloom_filter_fp_chance = 0.0001;```
7. Now, rebuild the SSTables with the new setting
```nodetool upgradesstables --include-all-sstables``` - this will take a while!

8. The *\*Filter* files will be somewhat bigger!

In a real world system, we can use ```nodetool cfstats``` to fine tune our system.