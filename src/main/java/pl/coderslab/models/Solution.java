package pl.coderslab.models;

public class Solution {
    private int id;
    private int exercise_id;
    private int user_id;
    private String created;
    private String updated;
    private String description;

    public Solution() {
    }

    public Solution(int exercise_id, int user_id, String created, String updated, String description) {
        this.exercise_id = exercise_id;
        this.user_id = user_id;
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public Solution(int id, int exercise_id, int user_id, String created, String updated, String description) {
        this.id = id;
        this.exercise_id = exercise_id;
        this.user_id = user_id;
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", exercise_id=" + exercise_id +
                ", user_id=" + user_id +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
