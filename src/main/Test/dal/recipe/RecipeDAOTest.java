package dal.recipe;

import dal.DALException;
import db.IConnPool;
import db.MySQL_DB;
import dto.recipe.IRecipeDTO;
import dto.recipe.RecipeDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeDAOTest {

    IConnPool iConnPool = new MySQL_DB();
    IRecipeDAO dao = new RecipeDAO();

    @Test
    public void createRecipe() throws DALException {
        RecipeDTO test = new RecipeDTO();
        test.setName("Pandekage");
        test.setRecipe_ID(1);
        LocalDate dateTest = new LocalDate(2019,05,10);
        LocalDate dateTest2 = new LocalDate(2020,05,10);
        test.setStartDate(dateTest);
        test.setStorageDate(dateTest2);

        dao.createRecipe(test);
        assertEquals("Pandekage",dao.getRecipe(1).getName());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM recipe WHERE recipe_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRecipeList() throws DALException {
        String name = "Pandekage";

        for (int i = 1; i < 4; i++) {
            RecipeDTO test = new RecipeDTO();
            test.setName(name);
            test.setRecipe_ID(i);
            LocalDate dateTest = new LocalDate(2019, 05, i);
            LocalDate dateTest2 = new LocalDate(2020, 05, i);
            test.setStartDate(dateTest);
            test.setStorageDate(dateTest2);

            dao.createRecipe(test);

            name = name + i;
        }

        List<IRecipeDTO> testList = new ArrayList<>();
        testList = dao.getRecipeList();

        assertEquals(3,testList.size());

        for (int i = 1; 1 < 4; i++){
            try (Connection c = iConnPool.getConn()){
                String secureState2 = "DELETE FROM recipe WHERE recipe_id = " + i;
                PreparedStatement state = c.prepareStatement(secureState2);
                state.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void getRecipe() throws DALException {
        RecipeDTO test = new RecipeDTO();
        test.setName("Æblekage");
        test.setRecipe_ID(1);
        LocalDate dateTest = new LocalDate(2019, 05, 01);
        LocalDate dateTest2 = new LocalDate(2020, 05, 01);
        test.setStartDate(dateTest);
        test.setStorageDate(dateTest2);

        dao.createRecipe(test);

        assertEquals("Æblekage",dao.getRecipe(1).getName());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM recipe WHERE recipe_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRecipe1() throws DALException {
        RecipeDTO test = new RecipeDTO();
        test.setName("Æblekage");
        test.setRecipe_ID(1);
        LocalDate dateTest = new LocalDate(2019, 05, 01);
        LocalDate dateTest2 = new LocalDate(2020, 05, 01);
        test.setStartDate(dateTest);
        test.setStorageDate(dateTest2);

        dao.createRecipe(test);

        assertEquals(1,dao.getRecipe("Æblekage").getRecipe_ID());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM recipe WHERE recipe_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRecipe2() throws DALException {
        RecipeDTO test = new RecipeDTO();
        test.setName("Æbletærte");
        test.setRecipe_ID(1);
        LocalDate dateTest = new LocalDate(2019, 05, 01);
        LocalDate dateTest2 = new LocalDate(2020, 05, 01);
        test.setStartDate(dateTest);
        test.setStorageDate(dateTest2);

        dao.createRecipe(test);

        LocalDate dateTest3 = new LocalDate(2019,9,1);


        assertEquals(1,dao.getRecipe("Æbletærte",dateTest3).getRecipe_ID());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM recipe WHERE recipe_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateRecipe() throws DALException {
        RecipeDTO test = new RecipeDTO();
        test.setName("Æbletærte");
        test.setRecipe_ID(1);
        LocalDate dateTest = new LocalDate(2019, 05, 01);
        LocalDate dateTest2 = new LocalDate(2020, 05, 01);
        test.setStartDate(dateTest);
        test.setStorageDate(dateTest2);

        dao.createRecipe(test);
        assertEquals("Æbletærte",dao.getRecipe(1).getName());

        test.setName("Iskage");

        dao.updateRecipe(test);
        assertEquals("Iskage",dao.getRecipe(1).getName());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM recipe WHERE recipe_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}