package sandura.mhdatabase.kitchen.ingredient;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DrinkIngredientRepository {

    public static final String DATABASE_FILE_PATH = "./src/main/resources/felyne-kitchen/ingredient/drink_ingredient.db";

    private final Logger logger = Logger.getLogger(DrinkIngredientRepository.class.getName());

    private List<String> databaseFileContents;

    private final Map<String, List<String>> drinkMap;

    public DrinkIngredientRepository() {
        drinkMap = new HashMap<>();
    }

    public void loadDataFromDirectory(String directory) {
        try {
            Path path = Paths.get(directory);
            databaseFileContents = Files.readAllLines(path).stream().skip(1).toList();
            databaseFileContents.forEach(drinkRow -> {
                logger.fine("Processing Drink row " + drinkRow);
                String key = drinkRow.split(",")[0];
                String value = drinkRow.split(",")[1];
                List<String> strings = drinkMap.get(key);
                if (strings == null) {
                    ArrayList<String> valuesList = new ArrayList<>();
                    valuesList.add(value);
                    drinkMap.put(key, valuesList);
                } else {
                    strings.add(value);
                }
            });
            logger.log(Level.INFO, "Loaded " + databaseFileContents.size() + " drink ingredients");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Loading DrinkIngredientRepository did not work." + e);
        }
    }

    public List<String> getDatabaseFileContents() {
        return databaseFileContents;
    }

    public Map<String, List<String>> getDrinkMap() {
        return drinkMap;
    }
}
