package sandura.mhdatabase.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sandura.mhdatabase.item.ItemRepository;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.RecipeRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DatabaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        PrintWriter writer = resp.getWriter();

        ItemRepository var1 = new ItemRepository();
        RecipeRepository var2 = new RecipeRepository();
        writer.println(var2.generatePossibleRecipes("Cubesteak", "Hardtack", 2));
        writer.println("Contents of Items Repository");
        writer.println(var1.getItems());
        FelyneRecipesService var3 = new FelyneRecipesService();
        ArrayList var4 = new ArrayList();
        var4.add("Chili Cheese");
        var4.add("Wild Wonton");
        writer.println("Possible ingredient from " + var4);
        writer.println(var3.getPossibleIngredientPairs(var4).toString());
        var3.getNumberOfCooksFromIngredient("Chili Cheese");

    }

}
