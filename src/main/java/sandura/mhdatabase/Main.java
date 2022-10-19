package sandura.mhdatabase;

import sandura.mhdatabase.item.ItemRepository;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.RecipeRepository;
import sandura.mhdatabase.kitchen.ingredient.DrinkIngredientRepository;
import sandura.mhdatabase.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Logger log = new Logger(Logger.LoggingLevel.INFO);

        ItemRepository itemRepository = new ItemRepository();


        RecipeRepository recipeRepository = new RecipeRepository();
        log.logInfo(recipeRepository.getRecipes().toString());
        log.logInfo(recipeRepository.generatePossibleRecipes("Aged Cheese","Sweetbug",2));
        log.logDebug("Contents of Items Repository");
        log.logDebug(itemRepository.getItems());

        FelyneRecipesService recipiesService = new FelyneRecipesService();
        List<String> availableIngridients = new ArrayList<>();
//        availableIngridients.add("Cubesteak");
//        availableIngridients.add("Salmon");
//        availableIngridients.add("Hardtack");
        availableIngridients.add("Chili Cheese");
        availableIngridients.add("Wild Wonton");
        availableIngridients.add("Octofest");
        availableIngridients.add("Buffalo Butter");
        availableIngridients.add("Sweet Mushroom");
        availableIngridients.add("Sweetbug");
        availableIngridients.add("Spicepop");
//        availableIngridients.add("Horseshoe Crab");
        availableIngridients.add("Warwheat");
//        availableIngridients.add("Fruity Jam");
//        availableIngridients.add("Cudgel Onion");
//        availableIngridients.add("Snake salmon");
        recipiesService.printPossibleDishes(availableIngridients);
    }


    void runTests(){

    }
}
