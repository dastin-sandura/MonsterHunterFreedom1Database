package sandura.mhdatabase.kitchen;

import sandura.mhdatabase.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class FelyneRecipesService {

    Logger log = new Logger(Logger.LoggingLevel.INFO);

    public void printPossibleDishes(List<String> ingredients) {

        List<String> ingredientPairs = new ArrayList<>();

        for (String ingridient : ingredients) {
            for (String secondIngredient : ingredients) {
                if (ingridient.equals(secondIngredient)) {
                    continue;
                }
                ingredientPairs.add(ingridient + "+" + secondIngredient);
            }
        }

        log.logDebug("generated ingredients pairs: " + ingredientPairs);

        RecipeRepository recipeRepository = new RecipeRepository();
        for (String pair : ingredientPairs) {
            String s = recipeRepository.getRecipes().get(pair);
            if (s != null) {
                log.logInfo("Pair "+pair+" has effect: " + s);
            }

        }


    }
}
