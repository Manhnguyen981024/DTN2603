import java.time.LocalDate;

public class Question {

    private int questionId;
    private String content;
    private int categoryId;
    private int typeId;
    private int creatorId;
    private LocalDate createDate;

    public Question(
            int questionId,
            String content,
            int categoryId,
            int typeId,
            int creatorId,
            LocalDate createDate) {

        this.questionId = questionId;
        this.content = content;
        this.categoryId = categoryId;
        this.typeId = typeId;
        this.creatorId = creatorId;
        this.createDate = createDate;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getContent() {
        return content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
