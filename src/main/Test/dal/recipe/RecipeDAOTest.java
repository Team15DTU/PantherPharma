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
import java.time.format.DateTimeFormatter;
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

        String str = "2019-05-10";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        String str2 = "2020-05-10";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime2 = LocalDate.parse(str2, formatter2);

        test.setStartDate(dateTime);
        test.setStorageDate(dateTime2);

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

            test.setRecipe_ID(1);String str = "2019-05-01";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTime = LocalDate.parse(str, formatter);

            String str2 = "2020-05-01";
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTime2 = LocalDate.parse(str2, formatter2);

            test.setStartDate(dateTime);
            test.setStorageDate(dateTime2);

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
        test.setRecipe_ID(1);String str = "2019-05-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        String str2 = "2020-05-01";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime2 = LocalDate.parse(str2, formatter2);
        test.setStartDate(dateTime);
        test.setStorageDate(dateTime2);

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
        test.setRecipe_ID(1);String str = "2019-05-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        String str2 = "2020-05-01";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime2 = LocalDate.parse(str2, formatter2);
        test.setStartDate(dateTime);
        test.setStorageDate(dateTime2);

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

        test.setRecipe_ID(1);String str = "2019-05-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        String str2 = "2020-05-01";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime2 = LocalDate.parse(str2, formatter2);

        test.setStartDate(dateTime);
        test.setStorageDate(dateTime2);

        dao.createRecipe(test);


        String str3 = "2019-09-01";
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime3 = LocalDate.parse(str3, formatter3);


        assertEquals(1,dao.getRecipe("Æbletærte",dateTime3).getRecipe_ID());

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

        test.setRecipe_ID(1);String str = "2019-05-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(str, formatter);

        String str2 = "2020-05-01";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime2 = LocalDate.parse(str2, formatter2);

        test.setStartDate(dateTime);
        test.setStorageDate(dateTime2);

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