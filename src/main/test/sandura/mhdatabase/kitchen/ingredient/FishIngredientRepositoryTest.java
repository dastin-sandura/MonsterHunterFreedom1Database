package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;

public class FishIngredientRepositoryTest {

    private static final Logger logger = new Logger(Logger.LoggingLevel.DEBUG);
    public static final int expected_fish_count = 15;

    public static void main(String[] args) throws IOException {
        FishIngredientRepositoryTest test = new FishIngredientRepositoryTest();
        test.testConstructorOfFishIngredientRepository();

    }

    public void testConstructorOfFishIngredientRepository() throws IOException {
        FishIngredientRepository repository = new FishIngredientRepository();
        repository.loadDataFromFile(FishIngredientRepository.DATABASE_FILE_PATH);
        int size = repository.getDbFileLines().size();
        System.out.println(repository.getFishMap());
        boolean b = size == expected_fish_count;
        if(!b) {
            logger.logError("Test failed. Expected " + expected_fish_count + " line in the Fish Ingredient Repository but found " + size);
        } else {
          logger.logInfo("Test has passed");
        }

    }
}
