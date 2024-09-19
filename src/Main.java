import dao.Dao;
import dao.HashMapMovieDao;
import domain.Movie;
import service.RentalInfoService;
import service.RentalInfoServiceFactory;

public class Main {

  public static void main(String[] args) {
    Dao<String, Movie> movieDao = HashMapMovieDao.getInstance();
    RentalInfoService<String> rentalInfoService = RentalInfoServiceFactory.getInstance().getStringRentalService(movieDao);

    AcceptanceTestSuit acceptanceTestSuit = new AcceptanceTestSuit(rentalInfoService);
    acceptanceTestSuit.runTests();

    System.out.println("Success");
  }
}
