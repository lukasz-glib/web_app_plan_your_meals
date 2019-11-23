package pl.files.model;

public class Recipe {
    private int id;

    public Recipe() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String ingredients;
    private String description;
    private String created;
    private String updated;
    private int preparationTime;
    private String preparation;
    private int adminId;

    public Recipe(String name, String ingredients, String description, int preparationTime, String preparation, int adminID) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.adminId = adminID;
    }

    public Recipe(String name, String ingredients, String description, int preparationTime, String preparation, int adminID, int id) {
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.preparationTime = preparationTime;
        this.preparation = preparation;
        this.adminId = adminID;
        this.id = id;
    }



    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", descripiton='" + description + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", preparation_time=" + preparationTime +
                ", preparation='" + preparation + '\'' +
                '}';
    }
}
