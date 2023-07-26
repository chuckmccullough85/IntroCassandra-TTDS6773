package cassandra.training;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchStatementBuilder;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.BatchableStatement;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.protocol.internal.request.Batch;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("datastax-java-driver.basic.request.timeout", "5 seconds");
        Movies mdb = new Movies("./src/resources/movies.json");
        createMovieDb(mdb);
    }

    public static void createMovieDb(Movies mdb) throws Exception {
        try (CqlSession sess = CqlSession.builder()
            .build()) {
            
            sess.execute("Drop keyspace if exists java_movies");
            sess.execute("create keyspace java_movies " + 
                         "WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'} ");
            sess.execute("CREATE TABLE java_movies.movies (" + 
                    "year int," + 
                    "title text," + 
                    "id int," + 
                    "genre text," +
                    "rating text," +
                    "cast set<text>," + 
                    "PRIMARY KEY (year, title, id))");
            sess.execute("CREATE TABLE java_movies.actors (name text, id int, PRIMARY KEY (name, id))");

            long startTime = new Date().getTime();
            int count = 0;
            int batchcount = 0;
            BatchStatementBuilder batch = BatchStatement.builder(BatchType.UNLOGGED);
            for(JsonMovie m : mdb.getMovies()) {
                try {
                PreparedStatement stmt = sess
                    .prepare("INSERT INTO java_movies.movies (id, title, year, genre, rating, cast)" +
                             "VALUES (?,?,?,?,?,?)");
                Set<String> cSet = new HashSet<>();
                cSet.addAll(Arrays.asList(m.getCast()));
                BoundStatement bs = stmt.bind(m.getId(), m.getTitle(), m.getYear(), m.getGenre(), m.getRating(), cSet);
                batch.addStatement(bs);
                batchcount++;
                if (batchcount >= 20) {
                    BatchStatement batchstmt =batch.build();
                    sess.execute(batchstmt);
                    batchcount = 0;
                    batch.clearStatements();
                }
                count++;
                }catch (Exception e)
                {
                    System.out.println(m.getId() +"\t"+ m.getTitle());
                    System.out.println(e);
                }
            }
            sess.execute(batch.build());
            batch.clearStatements();
            batchcount = 0;
            int aid = 1;
            for (String actor : mdb.getActors()) {
                if (actor == null) continue;
                actor = actor.trim();
                if (actor.length() == 0) continue;
                PreparedStatement stmt = sess.prepare("INSERT INTO java_movies.actors (id, name) VALUES (?,?)");
                BoundStatement bs = stmt.bind(aid, actor);
                batch.addStatement(bs);
                batchcount++;
                if (batchcount >= 20) {
                    sess.execute(batch.build());
                    batchcount = 0;
                    batch.clearStatements();
                }
                count++;
                aid++;
            }
            sess.execute(batch.build());
            long totalTime = new Date().getTime() - startTime;
            System.out.println("Inserted " + count + " records in " + totalTime +"ms");
        }

    }
}
