package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.kitchen.RecipeRepository;

import java.util.Map;

public class RecipeRepositoryTest {

    public static void main(String[] args) {
        test();
    }


    static void test() {
        RecipeRepository recipeRepository = new RecipeRepository();
        String result = recipeRepository.generatePossibleRecipes("Cubesteak", "Hardtack", 2);
        Map<String, String> recipes = recipeRepository.getRecipes();
        System.out.println(recipes.get("Spicy Sausage+Tuna Head"));
        System.out.println(recipes);
        if (!result.equals("Health+20")) {
            throw new Error("Recipe repository is not working. This case should return Health+20 and it returned " + result);
        }

    }
}
