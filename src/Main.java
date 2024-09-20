import dao.Dao;
import dao.HashMapMovieDao;
import domain.Movie;
import service.RentalInfoService;
import service.RentalInfoServiceFactory;
import service.calculation.RentalCalculationStrategyFactory;

public class Main {

  public static void main(String[] args) {
    Dao<String, Movie> movieDao = HashMapMovieDao.getInstance();

    RentalCalculationStrategyFactory rentalCalculationStrategyFactory = RentalCalculationStrategyFactory.getInstance();

    RentalInfoService<String> rentalInfoService = RentalInfoServiceFactory.getInstance().getStringRentalService(movieDao, rentalCalculationStrategyFactory);

    AcceptanceTestSuit acceptanceTestSuit = new AcceptanceTestSuit(rentalInfoService);
    acceptanceTestSuit.runTests();

    UnitTestSuit unitTestSuit = new UnitTestSuit();
    unitTestSuit.runTests();

    System.out.println("Success");
  }
}
