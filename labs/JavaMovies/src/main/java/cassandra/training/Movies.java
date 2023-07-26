package cassandra.training;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Movies {
    private List<JsonMovie> movies = new LinkedList<>();
    private Set<String> actors = new HashSet<>();

    public Movies(String jsonFile) throws Exception {
        ObjectMapper json = new ObjectMapper();
        try (BufferedReader infile = new BufferedReader(
                new FileReader(jsonFile))) {
            while (infile.ready()) {
                String line = infile.readLine();
                JsonMovie m = json.readValue(line, JsonMovie.class);
                movies.add(m);
                actors.addAll(Arrays.asList(m.getCast()));
            }
        }


    }

    public Iterable<JsonMovie> getMovies() {
        return movies;
    }

    public Iterable<String> getActors() {
        return actors;
    }

}
