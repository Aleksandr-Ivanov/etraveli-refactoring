package domain;

import java.util.Objects;
import java.util.Random;

public class Movie {
  public enum Code {REGULAR, NEW, CHILDREN}

  private int id;
  private String title;
  private Code code;

  public Movie(String title, Code code) {
    this.id = new Random().nextInt(Integer.MAX_VALUE);
    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public Code getCode() {
    return code;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Movie)) {
      return false;
    }
    Movie movie = (Movie) o;
    return id == movie.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Movie{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", code=" + code +
        '}';
  }
}
