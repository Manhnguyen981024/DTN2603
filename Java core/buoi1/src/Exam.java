import java.time.LocalDate;

public class Exam {

    private int examId;
    private String code;
    private String title;
    private int categoryId;
    private int duration;
    private int creatorId;
    private LocalDate createDate;

    public Exam(
            int examId,
            String code,
            String title,
            int categoryId,
            int duration,
            int creatorId,
            LocalDate createDate) {

        this.examId = examId;
        this.code = code;
        this.title = title;
        this.categoryId = categoryId;
        this.duration = duration;
        this.creatorId = creatorId;
        this.createDate = createDate;
    }

    public int getExamId() {
        return examId;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getDuration() {
        return duration;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
}
