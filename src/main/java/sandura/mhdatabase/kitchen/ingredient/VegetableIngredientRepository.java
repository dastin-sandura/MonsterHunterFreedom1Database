package sandura.mhdatabase.kitchen.ingredient;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import sandura.mhdatabase.logging.Logger;
import java.util.List;
public class VegetableIngredientRepository {

    private static final Logger log = new Logger(Logger.LoggingLevel.INFO);

    public List<String> dbLines;
    public VegetableIngredientRepository() {
        try {
            Files.readAllLines(Paths.get("./src/main/resources/felyne-kitchen/ingredient/vegetable_ingredient.db"));
        } catch (IOException ioe) {
            log.logError(ioe);
        }
    }
}
