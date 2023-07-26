package cassandra.training;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("datastax-java-driver.basic.request.timeout", "5 seconds");
        Movies mdb = new Movies("./src/resources/movies.json");
        createMovieDb(mdb);
    }

    public static void createMovieDb(Movies mdb) throws Exception {
        try (CqlSession sess = CqlSession.builder().build()) {
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
            for (JsonMovie m : mdb.getMovies()) {
                try {
                    // TODO: Create a prepared statement to insert a movie row into java_movies.movies
                    
                    // binding for C* set<text> is a java set
                    Set<String> cSet = new HashSet<>();
                    cSet.addAll(Arrays.asList(m.getCast()));

                    //TODO: create a BoundStatement, binding all the movie properties

                    //TODO: execute the statement
                    sess.execute(bs);
                    count++;
                } catch (Exception e) {
                    System.out.println(m.getId() + "\t" + m.getTitle());
                    System.out.println(e);
                }
            }
            int aid = 1;
            for (String actor : mdb.getActors()) {
                if (actor == null)
                    continue;
                actor = actor.trim();
                if (actor.length() == 0)
                    continue;

                //TODO: create a Prepared statement to insert actors
                //TODO: bind parameters
                //TODO: execute the statement    


                count++;
                aid++;
            }
            long totalTime = new Date().getTime() - startTime;
            System.out.println("Inserted " + count + " records in " + totalTime + "ms");
        }

    }
}
