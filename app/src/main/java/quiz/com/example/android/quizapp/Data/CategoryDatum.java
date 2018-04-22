package quiz.com.example.android.quizapp.Data;


public class CategoryDatum {

    private String name;
    private Integer imageId;
    private Integer colorId;

    public CategoryDatum(String name, Integer imageId, Integer colorId) {
        this.name = name;
        this.imageId = imageId;
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public Integer getColorId() {
        return colorId;
    }

}
