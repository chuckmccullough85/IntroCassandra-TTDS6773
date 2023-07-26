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
