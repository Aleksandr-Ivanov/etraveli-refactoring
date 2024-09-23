package domain;

import java.util.Objects;
import java.util.Random;

public class MovieRental {
  private int id;
  private String movieId;
  private int days;

  public MovieRental(String movieId, int days) {
    this.movieId = movieId;
    this.days = days;
    this.id = new Random().nextInt(Integer.MAX_VALUE);
  }

  public String getMovieId() {
    return movieId;
  }

  public int getDays() {
    return days;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MovieRental)) {
      return false;
    }
    MovieRental rental = (MovieRental) o;
    return id == rental.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "MovieRental{" +
        "id=" + id +
        ", movieId='" + movieId + '\'' +
        ", days=" + days +
        '}';
  }
}
