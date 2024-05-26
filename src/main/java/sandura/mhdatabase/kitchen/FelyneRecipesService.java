package sandura.mhdatabase.kitchen;

import sandura.mhdatabase.kitchen.ingredient.DrinkIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.FishIngredientRepository;
import sandura.mhdatabase.logging.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FelyneRecipesService {

    //TODO initialize all repositories in the constructor
    private DrinkIngredientRepository drinkIngredientRepository ;

    private FishIngredientRepository fishIngredientRepository;

    Logger log = new Logger(Logger.LoggingLevel.INFO);

    public FelyneRecipesService(){

    }

    Set<IngredientPair> pairs = new HashSet<>();

    public int getNumberOfCooksFromIngredient(String ingredientName) {

        return -1;
    }
    public Set<IngredientPair> getPossibleIngredientPairs(List<String> ingredients) {

        List<String> ingredientPairs = new ArrayList<>();

        for (String ingridient : ingredients) {
            for (String secondIngredient : ingredients) {
                if (ingridient.equals(secondIngredient)) {
                    continue;
                }
                ingredientPairs.add(ingridient + "+" + secondIngredient);
            }
        }

        List<IngredientPair> list = ingredientPairs.stream()
                .map(s -> new IngredientPair(s.split("\\+")[0], s.split("\\+")[1]))
                .toList();
        pairs.addAll(list);
        return pairs;
    }
}
