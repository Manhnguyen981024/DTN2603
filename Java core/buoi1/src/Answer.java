public class Answer {

    private int answerId;
    private String content;
    private int questionId;
    private boolean isCorrect;

    public Answer(int answerId, String content, int questionId, boolean isCorrect) {
        this.answerId = answerId;
        this.content = content;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public int getAnswerId() {
        return answerId;
    }

    public String getContent() {
        return content;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
