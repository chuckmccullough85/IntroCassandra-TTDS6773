# Overview
On your system, you will find a folder named *Cassandra Training*

That folder has the cassandra installation folder, *apache-cassandra-4.1.2*

Also, you will find the folder *studentwork*. That folder will contain the *labs* and solutions for the various hands-on labs.

Your system also contains **Visual Studio Code**.  Code is a open source, multi-platform, multi-language IDE. We recommend using VS Code for editing files in this course.

You can open VS Code from the command line by typing ```code .```.  This will open code in the current directory

## Starting/Stopping Cassandra 
### Standalone
1. Simply type ```cassandra``` at the command shell to start cassandra
2. Check the status of cassandra by typing ```nodetool status```
3. Stop cassandra by typing ```pgrep -f cassandra | xargs kill -9```

### Service (must have root permission)
Cassandra can also be run as a service.  This is beyond the scope of our course

All these commands are found in *apache-cassandra*/bin

## Lab

1. From a command shell, start cassandra and verify that it is running with nodetool.

1. Stop cassandra



