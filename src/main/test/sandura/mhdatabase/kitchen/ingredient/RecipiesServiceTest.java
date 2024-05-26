package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class RecipiesServiceTest {
private static final Logger log = new Logger(Logger.LoggingLevel.INFO);
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        FelyneRecipesService recipiesService = new FelyneRecipesService();
        List<String> availableIngredients = new ArrayList<>();
//        availableIngredients.add("Cubesteak");
//        availableIngredients.add("Salmon");
//        availableIngredients.add("Hardtack");
        availableIngredients.add("Chili Cheese");
        availableIngredients.add("Wild Wonton");
        availableIngredients.add("Octofest");
        availableIngredients.add("Buffalo Butter");
//        availableIngredients.add("Sweet Mushroom");
//        availableIngredients.add("Sweetbug");
//        availableIngredients.add("Spicepop");
//        availableIngredients.add("Horseshoe Crab");
//        availableIngredients.add("Warwheat");
//        availableIngredients.add("Fruity Jam");
//        availableIngredients.add("Cudgel Onion");
//        availableIngredients.add("Snake salmon");
        log.logInfo("Possible ingredient from " + availableIngredients);
        log.logInfo(recipiesService.getPossibleIngredientPairs(availableIngredients).toString());;
    }
}
