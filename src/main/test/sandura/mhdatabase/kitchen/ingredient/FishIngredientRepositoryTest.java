package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;

public class FishIngredientRepositoryTest {

    private static final Logger logger = new Logger(Logger.LoggingLevel.DEBUG);

    public static void main(String[] args) throws IOException {
        FishIngredientRepositoryTest test = new FishIngredientRepositoryTest();
        test.testConstructorOfFishIngredientRepository();

    }

    public void testConstructorOfFishIngredientRepository() throws IOException {
        FishIngredientRepository repository = new FishIngredientRepository();
        boolean b = repository.getDbFileLines().size() == 1;
        if(!b) {
            logger.logError("Test failed");
        }

    }
}
