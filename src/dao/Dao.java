package dao;

public interface Dao<ID, T> {
  T get(ID id);

  void save(ID id, T model);
}
