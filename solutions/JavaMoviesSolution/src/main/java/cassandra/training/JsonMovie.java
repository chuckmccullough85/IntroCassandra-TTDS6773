package cassandra.training;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMovie {
    private int id;
    private int year;
    private String title;
    private String rating;
    private String genre;
    private String[] cast;

    public JsonMovie(){}
    

    public JsonMovie(int id, int year, String title, String rating) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.rating = rating;
    }


    @JsonProperty("Id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("Year")
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    @JsonProperty("Title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @JsonProperty("Rated")
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    @JsonProperty("Genre")
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    @JsonProperty("Cast")
    public String[] getCast() {
        return cast;
    }
    public void setCast(String[] cast) {
        this.cast = cast;
    }
    
}
