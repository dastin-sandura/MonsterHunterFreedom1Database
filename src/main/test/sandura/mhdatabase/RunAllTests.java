package sandura.mhdatabase;

import sandura.mhdatabase.kitchen.ingredient.DrinkIngredientRepositoryTest;
import sandura.mhdatabase.kitchen.ingredient.FishIngredientRepositoryTest;
import sandura.mhdatabase.kitchen.ingredient.RecipeRepositoryTest;

import java.io.IOException;

public class RunAllTests {

    public static void main(String[] args) throws IOException {
        DrinkIngredientRepositoryTest.main(null);
        FishIngredientRepositoryTest.main(null);
        RecipeRepositoryTest.main(null);
    }
}
