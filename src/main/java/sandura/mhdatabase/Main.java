package sandura.mhdatabase;

import sandura.mhdatabase.item.ItemRepository;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.RecipeRepository;
import sandura.mhdatabase.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger log = new Logger(Logger.LoggingLevel.INFO);

    public static void main(String[] args) {
        ItemRepository itemRepository = new ItemRepository();

        RecipeRepository recipeRepository = new RecipeRepository();
//        log.logInfo(recipeRepository.getRecipes().toString());
        log.logInfo(recipeRepository.generatePossibleRecipes("Cubesteak", "Hardtack"));
        log.logDebug("Contents of Items Repository");
        log.logDebug(itemRepository.getItems());

        FelyneRecipesService recipiesService = new FelyneRecipesService();
        List<String> availableIngredients = new ArrayList<>();
//        availableIngredients.add("Cubesteak");
//        availableIngredients.add("Salmon");
//        availableIngredients.add("Hardtack");
        availableIngredients.add("Chili Cheese");
        availableIngredients.add("Wild Wonton");
//        availableIngredients.add("Octofest");
//        availableIngredients.add("Buffalo Butter");
//        availableIngredients.add("Sweet Mushroom");
//        availableIngredients.add("Sweetbug");
//        availableIngredients.add("Spicepop");
//        availableIngredients.add("Horseshoe Crab");
//        availableIngredients.add("Warwheat");
//        availableIngredients.add("Fruity Jam");
//        availableIngredients.add("Cudgel Onion");
//        availableIngredients.add("Snake salmon");
        log.logInfo("Possible ingredient from " + availableIngredients);
        log.logInfo(recipiesService.getPossibleIngredientPairs(availableIngredients).toString());
        recipiesService.getNumberOfCooksFromIngredient("Chili Cheese");

//        httpServerTests();
    }
}
