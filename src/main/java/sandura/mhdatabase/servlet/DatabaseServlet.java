package sandura.mhdatabase.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.IngredientPair;
import sandura.mhdatabase.kitchen.RecipeRepository;
import sandura.mhdatabase.kitchen.ingredient.DrinkIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.FishIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.GrainIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.MeatIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.MilkIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.OilIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.VegetableIngredientRepository;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;

public class DatabaseServlet extends HttpServlet {

    private static final RecipeRepository recipeRepository = new RecipeRepository();
    private final DrinkIngredientRepository drinkIngredientRepository = new DrinkIngredientRepository();

    private PrintWriter writer;
    private final Logger logger = Logger.getLogger(DatabaseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            logger.info(this.getClass().getName());
            req.getQueryString();
            String levelFiltering = req.getParameter("level-filter");

            logger.fine("level filter value is: " + levelFiltering);
            String[] ingredients = req.getParameterValues("ingredient");
            writer = resp.getWriter();

            printAsIs("<!doctype html>");
            printAsIs("<html lang=\"en-US\">");
            printAsIs("<head>");
            printAsIs("<title>MHFreedom Dish generator</title>");

            printAsIs("</head>");
            printAsIs("<body>");

            String webAppWebInfDirectory = getServletContext().getRealPath("/") + "WEB-INF/";

            String kitchenRecipeDbFilePath = webAppWebInfDirectory + "kitchen_recipe.db";

            FelyneRecipesService felyneRecipesService = new FelyneRecipesService();

            if (recipeRepository.getRecipes().keySet().isEmpty()) {
                logger.info("Loading data because recipe repo is empty");
                recipeRepository.loadDataFromBaseDir(kitchenRecipeDbFilePath);
            }

            try {
                List<String> requestParameters = new ArrayList<>();
                if (ingredients != null) {
                    requestParameters.addAll(Arrays.stream(ingredients).toList());
                }
                Set<IngredientPair> pairs = felyneRecipesService.getPossibleIngredientPairs(requestParameters);

                pairs.forEach(ingredientPair -> {
                    String optionalRecipe = recipeRepository.generatePossibleRecipes(ingredientPair.first, ingredientPair.second);
                    if (optionalRecipe != null) {
                        printAsIs("<h2>Effect from ingredients: " + ingredientPair.first + " & " + ingredientPair.second + "=" + optionalRecipe + "</h2>");
                    }
                });

            } catch (Exception e) {
                print(e.toString());
            }


            printAsIs("<form action=\"\" method=\"get\">");
            print("Level filtering");
            ArrayList<String> possibleLevels = new ArrayList<>();
            possibleLevels.add("1");
            possibleLevels.add("2");
            possibleLevels.add("3");
            possibleLevels.add("4");
            possibleLevels.add("5");
            possibleLevels.forEach(levelValue -> {
                printAsIs("<div>");
                printAsIs(levelValue);
                if (levelValue.equals(levelFiltering)) {
                    printAsIs("<input type=\"checkbox\" name=\"level-filter\" value=\"" + levelValue + "\" checked/>");
                } else {
                    printAsIs("<input type=\"checkbox\" name=\"level-filter\" value=\"" + levelValue + "\"/>");
                }
                printAsIs("</div>");
            });
            print("Drink ingredients - auto generated");
            Map<String, List<String>> drinkIngridientsMap = drinkIngredientRepository.getDrinkMap();
            if (drinkIngridientsMap.isEmpty()) {
                drinkIngredientRepository.loadDataFromDirectory(webAppWebInfDirectory + "drink_ingredient.db");
            }
            logger.fine("Value of ingredient map is " + drinkIngridientsMap);
            drinkIngridientsMap.forEach((level, ingredientList) -> {
                logger.log(FINE,"Inside foreach on the ingredient list");
                if (levelFiltering == null) {
                    ingredientList.forEach(ingredient -> {
                        printAsIs("<div>");
                        printAsIs(ingredient + "(level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + ingredient.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    ingredientList.forEach(ingredient -> {
                        printAsIs("<div>");
                        printAsIs(ingredient + "(level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + ingredient.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });
            logger.fine("Finished printing ingredients");
            FishIngredientRepository fishIngredientRepository = new FishIngredientRepository();
            fishIngredientRepository.loadDataFromFile(webAppWebInfDirectory + "fish_ingredient.db");

            print("Fish ingredients - generated from file");

            fishIngredientRepository.getFishMap().forEach((level, fishes) -> {
                if (levelFiltering == null) {
                    fishes.forEach(fish -> {
                        printAsIs("<div>");
                        printAsIs(fish + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + fish.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (Integer.valueOf(levelFiltering).equals(level)) {
                    fishes.forEach(fishe -> {
                        printAsIs("<div>");
                        printAsIs(fishe + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + fishe.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });
            print("Vegetable list ingredients - generated from file");

            VegetableIngredientRepository vegetableIngredientRepository = new VegetableIngredientRepository();
            vegetableIngredientRepository.loadDataFromBaseDir(webAppWebInfDirectory + "vegetable_ingredient.db");
            vegetableIngredientRepository.getVegetableMap().forEach((level, strings) -> {
                if (levelFiltering == null) {
                    logger.fine("Vegetables list for level " + level + " has values " + strings);
                    strings.forEach(vegetable -> {
                        printAsIs("<div>");
                        printAsIs(vegetable + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + vegetable.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    logger.fine("Vegetables list for level " + level + " has values " + strings);
                    strings.forEach(vegetable -> {
                        printAsIs("<div>");
                        printAsIs(vegetable + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + vegetable.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });

            print("Milk list ingredients - generated from file");

            MilkIngredientRepository milkIngredientRepository = new MilkIngredientRepository();
            milkIngredientRepository.loadDataFromBaseDir(webAppWebInfDirectory + "milk_ingredient.db");
            milkIngredientRepository.getMilkMap().forEach((level, strings) -> {
                if (levelFiltering == null) {
                    logger.fine("Milk list for level " + level + " has values " + strings);
                    strings.forEach(milk -> {
                        printAsIs("<div>");
                        printAsIs(milk + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + milk.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    logger.fine("Milk list for level " + level + " has values " + strings);
                    strings.forEach(milk -> {
                        printAsIs("<div>");
                        printAsIs(milk + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + milk.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });

            print("Meat list ingredients - generated from file");

            MeatIngredientRepository meatIngredientRepository = new MeatIngredientRepository();
            meatIngredientRepository.loadDataFromBaseDir(webAppWebInfDirectory + "meat_ingredient.db");
            meatIngredientRepository.getMeatMap().forEach((level, strings) -> {
                if (levelFiltering == null) {
                    logger.fine("Meat list for level " + level + " has values " + strings);
                    strings.forEach(meat -> {
                        printAsIs("<div>");
                        printAsIs(meat + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + meat.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    logger.fine("Meat list for level " + level + " has values " + strings);
                    strings.forEach(meat -> {
                        printAsIs("<div>");
                        printAsIs(meat + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + meat.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });

            print("Oil list ingredients - generated from file");

            OilIngredientRepository oilIngredientRepository = new OilIngredientRepository();
            oilIngredientRepository.loadDataFromBaseDir(webAppWebInfDirectory + "oil_ingredient.db");
            oilIngredientRepository.getOilMap().forEach((level, strings) -> {
                if (levelFiltering == null) {
                    logger.fine("Oil list for level " + level + " has values " + strings);
                    strings.forEach(oil -> {
                        printAsIs("<div>");
                        printAsIs(oil + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + oil.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    logger.fine("Oil list for level " + level + " has values " + strings);
                    strings.forEach(oil -> {
                        printAsIs("<div>");
                        printAsIs(oil + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + oil.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });

            print("Grain list ingredients - generated from file");

            GrainIngredientRepository grainIngredientRepository = new GrainIngredientRepository();
            grainIngredientRepository.loadDataFromBaseDir(webAppWebInfDirectory + "grain_ingredient.db");
            grainIngredientRepository.getGrainMap().forEach((level, strings) -> {
                if (levelFiltering == null) {
                    logger.fine("Grain list for level " + level + " has values " + strings);
                    strings.forEach(grain -> {
                        printAsIs("<div>");
                        printAsIs(grain + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + grain.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (levelFiltering.equals(level)) {
                    logger.fine("Grain list for level " + level + " has values " + strings);
                    strings.forEach(grain -> {
                        printAsIs("<div>");
                        printAsIs(grain + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + grain.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });

            printAsIs("""
                    <div>
                        <input type="submit" value="Generate" />
                    </div>
                    """);
            printAsIs("</form>");
            felyneRecipesService.getNumberOfCooksFromIngredient("Chili Cheese");

            printAsIs("<ol>");
            if (levelFiltering == null) {
                print("All combinations loaded from file:");
                logger.info("Printing all recipies without filtering");
                recipeRepository.getRecipes().forEach((s, s2) -> printAsIs("<li>" + s + " = " + s2 + "</li>"));
            } else {
                print("Combinations loaded from file only when you have " + levelFiltering + " Cat cooks.");
                logger.info("Printing all recipes for cook count " + levelFiltering);
                recipeRepository.getRecipeByCookCountMap().get(Integer.parseInt(levelFiltering)).forEach(s -> printAsIs("<li>" + s + "</li>"));
            }
            printAsIs("</ol>");
            printAsIs("</body>");
            printAsIs("</html>");

            writer = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Something broke in the servlet " + e);
        }
    }

    private void printAsIs(String s) {
        writer.println(s);
    }

    private void print(String s) {
        writer.println("<p>" + s + "</p>");
    }

}
