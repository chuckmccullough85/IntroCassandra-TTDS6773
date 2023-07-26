# Overview
This project will be our initial setup for using Java with Cassandra.

| | |
| --------- | --------------------------- |
| Exercise Folder | GettingStartedJava/getting-started |
| Builds On | None |
| Time to complete | 30 minutes

---

## Steps
1. Import the maven project (pom.xml) in the lab folder into your IDE
2. Build and run - verify *Hello World* output
3. Define a new class named *QueryCompaction*
4. Create a static method in the class named *ShowHistory()*
5. Create a session to the local Cassandra
6. Query the **system.compaction_history** table
7. Display the results
8. Add a call from *main()*
9. Run the application and verify table output


:::spoiler

```java
package cassandra.training;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

public class QueryCompaction {
    public static void showHistory() {
        try (CqlSession sess = CqlSession.builder().build()) {
            ResultSet rs = sess.execute(
                    "select columnfamily_name, compacted_at from system.compaction_history");
            for (Row row : rs) {
                System.out.println(row.getString(0) + ":\t "
                        + row.getInstant(1));
            }
        }
    }
}

```

:::