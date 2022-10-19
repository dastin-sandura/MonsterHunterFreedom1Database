package sandura.mhdatabase.kitchen;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeRepository {

    private Logger logger = new Logger(Logger.LoggingLevel.INFO);

    class IngridientsPair {
        String firstIndredient;
        String secondIngredient;

        IngridientsPair(String first, String second) {
            firstIndredient = first;
            secondIngredient = second;
        }

        public String toString() {
            return "Ingredient Pair: " + firstIndredient + ", " + secondIngredient;
        }
    }

    Map<String, String> recipeMap;

    public RecipeRepository() {
        recipeMap = new HashMap<>();
        try {
            List<String> strings = Files.readAllLines(Paths.get("./src/main/resources/kitchen_recipe.db"));
            for (int i = 0; i < strings.size(); i++) {
                if (i == 0) {
                    logger.logDebug("Reading table with headers:");
                    logger.logDebug(strings.get(i));
                } else {
                    String row = strings.get(i);
                    logger.logDebug(row);
                    String[] split = row.split(",");
                    String[] ingredients = split[4].split("\\+");
                    recipeMap.put(ingredients[0] + "+" + ingredients[1], split[3]);
                }

            }
        } catch (IOException ioe) {
            logger.logError(ioe);
        }
    }

    public Map<String, String> getRecipes() {
        return recipeMap;
    }

    public String generatePossibleRecipes(String first, String second, int chefsCount) {
        logger.logInfo("Possible combinations:");
        return recipeMap.get(first + "+" + second);
    }
}
