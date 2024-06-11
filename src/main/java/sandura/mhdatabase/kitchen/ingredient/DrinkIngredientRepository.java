package sandura.mhdatabase.kitchen.ingredient;


import java.io.IOException;
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

    private Logger logger = Logger.getLogger(DrinkIngredientRepository.class.getName());

    private List<String> databaseFileContents;

    private Map<Integer, List<String>> asMap;

    public DrinkIngredientRepository() {
        asMap = new HashMap<>();
//        loadDataFromDirectory(DATABASE_FILE_PATH);
    }

    public void loadDataFromDirectory(String directory) {
        try {
            Path path = Paths.get(directory);
            databaseFileContents = Files.readAllLines(path).stream().skip(1).toList();
            databaseFileContents.forEach(drinkRow -> {
                logger.fine("Processing Drink row " + drinkRow);
                String key = drinkRow.split(",")[0];
                String value = drinkRow.split(",")[1];
                List<String> strings = asMap.get(key);
                if (strings == null) {
                    ArrayList<String> valuesList = new ArrayList<>();
                    valuesList.add(value);
                    asMap.put(Integer.parseInt(key), valuesList);
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

    public Map<Integer, List<String>> getAsMap() {
        return asMap;
    }
}
