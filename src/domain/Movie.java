package domain;

public class Movie {
  public enum Code {REGULAR, NEW, CHILDREN}
  private String title;
  private Code code;

  public Movie(String title, Code code) {

    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public Code getCode() {
    return code;
  }
}
