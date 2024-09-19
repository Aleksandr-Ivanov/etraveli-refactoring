package dao;

import domain.Movie;

import java.util.HashMap;

import static domain.Movie.*;

public class HashMapMovieDao implements Dao<String, Movie> {
  private static final HashMapMovieDao instance = new HashMapMovieDao();

  private final HashMap<String, Movie> movies;

  private  HashMapMovieDao() {
    movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", Code.REGULAR));
    movies.put("F002", new Movie("Matrix", Code.REGULAR));
    movies.put("F003", new Movie("Cars", Code.CHILDREN));
    movies.put("F004", new Movie("Fast & Furious X", Code.NEW));
  }

  public static HashMapMovieDao getInstance() {
    return instance;
  }

  @Override
  public Movie get(String id) {
    return movies.get(id);
  }

  @Override
  public void save(String id, Movie model) {
    movies.put(id, model);
  }
}
