package service;

import dao.Dao;
import domain.Movie;

public class RentalInfoServiceFactory {
  private static final RentalInfoServiceFactory instance = new RentalInfoServiceFactory();
  private RentalInfoService<String> cached = null;

  private RentalInfoServiceFactory() {}

  public static RentalInfoServiceFactory getInstance() {
    return instance;
  }

   public RentalInfoService<String> getStringRentalService(Dao<String, Movie> dao) {
     if (cached == null) {
       cached = new StringRentalInfoService((dao));
     }
     return cached;
   }
}
