package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class OilIngredientRepository {

    private static final Logger log = new Logger(Logger.LoggingLevel.INFO);

    List<String> dbLines;

    OilIngredientRepository() {
        try {
            dbLines = Files.readAllLines(Paths.get("./src/main/resources/felyne-kitchen/ingredient/oil_ingredient.db"));
        } catch (IOException ioe) {
            log.logError(ioe);
        }
    }
}
