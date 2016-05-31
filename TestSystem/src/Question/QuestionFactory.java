package Question;

import Question.Question;

/**
 * Created by Zheyu Lu on 2016/5/31.
 */
public class QuestionFactory {
    public class QuestionType {
        public final static int DECIDE = 0;
        public final static int CHOICE = 1;
        public final static int ESSAY = 3;
        public final static int MAP = 5;
        public final static int RANK = 4;
        public final static int SHORTQUESTION = 2;
    }

    /**
     * 生产指定类型的Question对象，如果生产失败会抛出RuntimeException
     *
     * @param type 必须是QuestionType中的某个值
     * @return 生产出来的Question对象
     */
    public Question createQuestion(int type) {
        switch (type) {
            case QuestionType.DECIDE:
                return new DecideQuestion();
            case QuestionType.CHOICE:
                return new ChoiceQuestion();
            case QuestionType.ESSAY:
                return new EssayQuestion();
            case QuestionType.MAP:
                return new MapQuestion();
            case QuestionType.RANK:
                return new RankQuestion();
            case QuestionType.SHORTQUESTION:
                return new ShortEssayQuestion();
            default:
                throw new RuntimeException("No such Question type!");
        }
    }
}
