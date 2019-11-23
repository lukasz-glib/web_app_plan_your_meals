package pl.files.dao;

import pl.files.exception.NotFoundException;

import pl.files.model.Recipe;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe(name, ingredients, description, created, updated, preparation_time, preparation, admin_id) VALUES (?, ?, ?, NOW(), NOW(), ?, ?, ?);";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPE_QUERY = "SELECT * FROM recipe ORDER BY updated DESC;";
    private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY = "UPDATE	recipe SET name = ? , ingredients = ?, description = ? , updated = NOW() , preparation_time = ?, preparation = ?, admin_id = ? WHERE id = ?;";
    private static final String NUMBER_OF_RECIPES_ADDED = "SELECT COUNT(*) number FROM recipe WHERE admin_id = ?;";
    private static final String FIND_ALL_USERS_RECIPES_BY_CREATED_QUERY = "SELECT * FROM recipe WHERE admin_id = ? ORDER BY updated DESC";
    private static final String FIND_RECIPES_BY_NAME = "SELECT * FROM recipe WHERE LOWER(name) LIKE ? ORDER BY updated DESC;";

    public ArrayList<Recipe> findRecipesByName(String name) {
//        if(name.isEmpty()){
//            final String FIND_RECIPES_BY_NAME = "SELECT * FROM recipe WHERE LOWER(name) LIKE ? ORDER BY updated DESC;";
//            try (PreparedStatement preparedStatement = DbUtil.prepareStatement(FIND_RECIPES_BY_NAME, false)){
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            final String FIND_RECIPES_BY_NAME = "SELECT * FROM recipe WHERE LOWER(name) LIKE ? ORDER BY updated DESC;";
//        }
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.FIND_RECIPES_BY_NAME, false)) {
            name = name.trim().toLowerCase();
//            if (name.isEmpty()) {
//                final String FIND_RECIPES_BY_NAME = "SELECT * FROM recipe WHERE LOWER(name) LIKE ? ORDER BY updated DESC;";
//                preparedStatement.setString(1, name);
//            } else {
//                preparedStatement.setString(1, "%" + name.replaceAll(" ", "%'' AND ''%") + "%");
//            }
            preparedStatement.setString(1, ("%" + name.replace(" ", "%") + "%"));

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Recipe> recipes = new ArrayList<>();

            while (resultSet.next()) {
                recipes.add(new Recipe(
                        resultSet.getString("name"), resultSet.getString("ingredients"), resultSet.getString("description"),
                        resultSet.getInt("preparation_time"), resultSet.getString("preparation"), resultSet.getInt("admin_id")
                ));
            }
            return (ArrayList<Recipe>) recipes;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Recipe create(Recipe recipe) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.CREATE_RECIPE_QUERY, true)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, recipe.getIngredients());
            preparedStatement.setString(3, recipe.getDescription());
            preparedStatement.setInt(4, recipe.getPreparationTime());
            preparedStatement.setString(5, recipe.getPreparation());
            preparedStatement.setInt(6, recipe.getAdminId());


            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer recipeId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.DELETE_RECIPE_QUERY, true)) {
            preparedStatement.setInt(1, recipeId);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.FIND_ALL_RECIPE_QUERY, true);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients(resultSet.getString("ingredients"));
                recipeToAdd.setDescription(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getString("created"));
                recipeToAdd.setUpdated(resultSet.getString("updated"));
                recipeToAdd.setPreparationTime(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipeToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.READ_RECIPE_QUERY, true)
        ) {
            preparedStatement.setInt(1, recipeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    recipe.setId(resultSet.getInt("id"));
                    recipe.setName(resultSet.getString("name"));
                    recipe.setIngredients(resultSet.getString("ingredients"));
                    recipe.setDescription(resultSet.getString("description"));
                    recipe.setCreated(resultSet.getString("created"));
                    recipe.setUpdated(resultSet.getString("updated"));
                    recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    recipe.setPreparation(resultSet.getString("preparation"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public void update(Recipe recipe) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.UPDATE_RECIPE_QUERY, true)) {
            preparedStatement.setString(1, recipe.getName());
            preparedStatement.setString(2, recipe.getIngredients());
            preparedStatement.setString(3, recipe.getDescription());
//            preparedStatement.setString(4, recipe.getCreated());
//            preparedStatement.setString(5, recipe.getUpdated());
            preparedStatement.setInt(4, recipe.getPreparationTime());
            preparedStatement.setString(5, recipe.getPreparation());
            preparedStatement.setInt(6, recipe.getAdminId());
            preparedStatement.setInt(7, recipe.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int numberOfRecipesAdded(int adminId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.NUMBER_OF_RECIPES_ADDED, false)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("number");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Recipe> findAllUsersRecipes(int adminId) {
        List<Recipe> recipeList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipeDao.FIND_ALL_USERS_RECIPES_BY_CREATED_QUERY, true)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recipe recipeToAdd = new Recipe();
                recipeToAdd.setId(resultSet.getInt("id"));
                recipeToAdd.setName(resultSet.getString("name"));
                recipeToAdd.setIngredients("ingredients");
                recipeToAdd.setDescription(resultSet.getString("description"));
                recipeToAdd.setCreated(resultSet.getString("created"));
                recipeToAdd.setUpdated(resultSet.getString("updated"));
                recipeToAdd.setPreparationTime(resultSet.getInt("preparation_time"));
                recipeToAdd.setPreparation(resultSet.getString("preparation"));
                recipeList.add(recipeToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

}

