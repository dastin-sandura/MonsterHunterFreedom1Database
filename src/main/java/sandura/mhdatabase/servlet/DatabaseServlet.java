package sandura.mhdatabase.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sandura.mhdatabase.item.ItemRepository;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.RecipeRepository;
import sandura.mhdatabase.kitchen.ingredient.DrinkIngredientRepository;
import sandura.mhdatabase.kitchen.ingredient.FishIngredientRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseServlet extends HttpServlet {

    private static final RecipeRepository recipeRepository = new RecipeRepository();
    private final DrinkIngredientRepository drinkIngredientRepository = new DrinkIngredientRepository();

    PrintWriter writer;
    private Logger logger = Logger.getLogger(DatabaseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
//        Logger.getLogger (DatabaseServlet.class.getName()).log(Level.WARNING, e.getMessage(), e);
            logger.log(Level.INFO, "hello world");
            req.getQueryString();
            String first = req.getParameter("first");
            String second = req.getParameter("second");
            String levelFiltering = req.getParameter("level-filter");
            if ("null".equals(levelFiltering)) {
                logger.log(Level.INFO, "Chaning level-filter value to null");
                levelFiltering = null;
            } else if (levelFiltering == null){
                logger.log(Level.INFO, "level-filter param remains as special vlue null");
            } else {
                logger.log(Level.INFO, "level-filter param has not String null and not special null value so it has value '" + levelFiltering + "' so what the fuck is the value?");
            }
            final String finalLevelFiltering = levelFiltering;

            logger.log(Level.INFO, "level filter value is: " + levelFiltering);
            String[] ingredients = req.getParameterValues("ingredient");
            if (ingredients != null && ingredients.length == 2) {
                first = ingredients[0];
                second = ingredients[1];

            }

            //        super.doGet(req, resp);
            writer = resp.getWriter();

            printAsIs("<!doctype html>");
            printAsIs("<html lang=\"en-US\">");
            printAsIs("<head>");
            printAsIs("<title>MHFreedom Dish generator</title>");

            printAsIs("</head>");
            printAsIs("<body>");

//        print("Value of environment variable 'catalina.base' is :" + System.getProperty("catalina.base"));
            String webAppWebInfDirectory = getServletContext().getRealPath("/").toString() + "WEB-INF/";

            String kitchenRecipeDbFilePath = webAppWebInfDirectory + "kitchen_recipe.db";
//        print("getServletContext().getRealPath(\"/\") : " + kitchenRecipeDbFilePath);
//        print("Attributes:");
//        getServletContext().getAttributeNames().asIterator().forEachRemaining(s -> print(s + " = " + getServletContext().getAttribute(s)));
//        print("First parameter %s".formatted(first));
//        print("Second parameter %s".formatted(second));

            FelyneRecipesService felyneRecipesService = new FelyneRecipesService();

            if (recipeRepository.getRecipes().keySet().isEmpty()) {
                logger.log(Level.INFO, "Loading data because recipe repo is empty");
                recipeRepository.loadDataFromBaseDir(kitchenRecipeDbFilePath);
            }
            if (first != null && second != null) {
                logger.log(Level.INFO, "Checking possibilites for " + first + " and " + second);
                List<String> requestParameters = new ArrayList<>();
                requestParameters.add(first);
                requestParameters.add(second);
//            print("Possible ingredient from " + requestParameters);
                print("Possible ingredient from " + felyneRecipesService.getPossibleIngredientPairs(requestParameters).toString());

                try {
                    printAsIs("<h2>Effect from ingredients: " + recipeRepository.generatePossibleRecipes(first, second, 0) + "</h2>");
                } catch (Exception e) {
                    print(e.toString());
                }

            }

            Map<Integer, List<String>> asMap = drinkIngredientRepository.getAsMap();
            if (asMap.isEmpty()) {
                drinkIngredientRepository.loadDataFromDirectory(webAppWebInfDirectory + "drink_ingredient.db");
            }
            printAsIs("<form action=\"\" method\"get\">");
            print("Level filtering");
            ArrayList<String> possibleLevels = new ArrayList<>();
            possibleLevels.add("1");
            possibleLevels.add("2");
            possibleLevels.add("3");
            possibleLevels.add("4");
            possibleLevels.add("5");
            printAsIs("<div>");
            printAsIs("Clear filtering");
            printAsIs("<input type=\"checkbox\" name=\"level-filter\" value=\"null\"/>");
            printAsIs("</div>");
            possibleLevels.forEach(levelValue -> {
                printAsIs("<div>");
                printAsIs(levelValue);
                if(finalLevelFiltering!=null && levelValue.equals(finalLevelFiltering) ){
                printAsIs("<input type=\"checkbox\" name=\"level-filter\" value=\"" + levelValue + "\" checked/>");

                } else{

                printAsIs("<input type=\"checkbox\" name=\"level-filter\" value=\"" + levelValue + "\"/>");
                }
                printAsIs("</div>");
            });
            print("Drink ingredients - auto generated");
            logger.log(Level.INFO,"finalLevelFiltering variable value = " + finalLevelFiltering);
            logger.log(Level.INFO, "Value of ingredient map is " + asMap);
            asMap.forEach((level, ingredientList) -> {
//                logger.log(Level.INFO,"Inside foreach on the ingredient list");
                if (finalLevelFiltering == null) {
                    ingredientList.forEach(ingredient -> {
                        printAsIs("<div>");
                        printAsIs(ingredient + "(level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + ingredient.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (Integer.valueOf(finalLevelFiltering).equals(level)) {
                    ingredientList.forEach(ingredient -> {
                        printAsIs("<div>");
                        printAsIs(ingredient + "(level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + ingredient.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                }
            });
            logger.log(Level.INFO, "Finished printing ingredients");
            FishIngredientRepository fishIngredientRepository = new FishIngredientRepository();
            fishIngredientRepository.loadDataFromFile(webAppWebInfDirectory + "fish_ingredient.db");
            logger.log(Level.INFO, fishIngredientRepository.getFishMap().toString());
            print("Fish ingredients - generated from file");

            fishIngredientRepository.getFishMap().forEach((level, fishes) -> {
                if (finalLevelFiltering == null) {
                    fishes.forEach(fishe -> {
                        printAsIs("<div>");
                        printAsIs(fishe + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + fishe.toLowerCase() + "\"/>");
                        printAsIs("</div>");
                    });
                } else if (Integer.valueOf(finalLevelFiltering).equals(level)) {
                    fishes.forEach(fishe -> {
                        printAsIs("<div>");
                        printAsIs(fishe + " (level " + level + ")");
                        printAsIs("<input type=\"checkbox\" name=\"ingredient\" value=\"" + fishe.toLowerCase() + "\"/>");
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
//        printAsIs("""
//                       <p>Manually created form</p>
//                       <form action="" method"get">
//                                   <div>
//                                       Cubesteak
//                                       <input type="checkbox" name="ingredient" value="Cubesteak" id="second"/>
//                                   </div>
//                                  <div>
//                                       Dry Margarine
//                                       <input type="checkbox" name="ingredient" value="Dry Margarine" id="second"/>
//                                   </div>
//
//                                   <div>
//                                       Tuna head
//                                       <input type="checkbox" name="ingredient" value="Tuna head" id="second"/>
//                                   </div>
//                                   <div>
//                                       Hardtack
//                                       <input type="checkbox" name="ingredient" value="Hardtack" id="second"/>
//                                   </div>
//
//                                   <div>
//                                       Snake salmon
//                                       <input type="checkbox" name="ingredient" value="Snake salmon" id="second"/>
//                                   </div>
//                                   <div>
//                                       Sweetbug
//                                       <input type="checkbox" name="ingredient" value="Sweetbug" id="second"/>
//                                   </div>
//
//                                   <div>
//                                       Spicy Sausage
//                                       <input type="checkbox" name="ingredient" value="Spicy Sausage" id="second"/>
//                                   </div>
//
//                                   <div>
//                                       Aged Cheese
//                                       <input type="checkbox" name="ingredient" value="Aged Cheese" id="second"/>
//                                   </div>
//                                   <div>
//                                       <input type="submit" value="Generate" />
//                                   </div>
//                               </form>
//                """);


            if (finalLevelFiltering == null) {
                print("All combinations loaded from file:");
                printAsIs("<ol>");
                logger.log(Level.INFO, "Printing all recipies without filtering");
                recipeRepository.getRecipes().forEach((s, s2) -> printAsIs("<li>" + s + " = " + s2 + "</li>"));
            } else {
                print("Combinations loaded from file only when you have " + finalLevelFiltering + " Cat cooks.");
                printAsIs("<ol>");
                logger.log(Level.INFO, "Printing all recipies for cook count " + finalLevelFiltering);
                recipeRepository.getRecipeByCookCountMap().get(Integer.parseInt(finalLevelFiltering)).forEach(s -> printAsIs("<li>" + s + "</li>"));
            }
            printAsIs("</ol>");
            printAsIs("</body>");
            printAsIs("</html>");

            writer = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "Someto");
            logger.log(Level.SEVERE, "Something broke " + e.getStackTrace().toString());
        }
    }

    private void printAsIs(String s) {
        writer.println(s);
    }

    private void print(String s) {
        writer.println("<p>" + s + "</p>");
    }

}
