package domain;

import java.util.Objects;

public class Movie {
  public enum Code {REGULAR, NEW, CHILDREN}

  private String id;
  private String title;
  private Code code;

  public Movie(String id, String title, Code code) {
    this.id = id;
    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public Code getCode() {
    return code;
  }

  public String getId() {
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
    return Objects.equals(id, movie.id);
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
