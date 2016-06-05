package answer;

import Question.*;

import java.util.concurrent.TimeoutException;

/**
 * 用于生产Answer对象
 * Created by Mengxiao Lin on 2016/5/29.
 */
public class AnswerFactory {
    public class AnswerType{
        public final static int DECIDE = 0;
        public final static int CHOICE = 1;
        public final static int TEXT= 2;
        public final static int RANK = 4;
        public final static int MAP = 5;
    }

    /**
     * 生产指定类型的Answer对象，如果生产失败会抛出RuntimeException
     * @param type 必须是AnswerType中的某个值
     * @return 生产出来的Answer对象
     */
    public Answer createAnswer(int type){
        switch (type){
            case AnswerType.DECIDE: return new DecideAnswer();
            case AnswerType.CHOICE: return new ChoiceAnswer();
            case AnswerType.TEXT: return new TextAnswer();
            case AnswerType.MAP:return new MapAnswer();
            case AnswerType.RANK: return new RankAnswer();
            default: throw new RuntimeException("No such answer type!");
        }
    }

    public Answer buildAnswerByQuestion(Question question, String answerStr){
        Answer ret = null;
        if (question instanceof DecideQuestion) ret= new DecideAnswer();
        if (question instanceof ChoiceQuestion) ret= new ChoiceAnswer();
        if (question instanceof MapQuestion) ret= new MapAnswer();
        if (question instanceof RankQuestion) ret= new RankAnswer();
        if (question instanceof EssayQuestion) ret = new TextAnswer();
        if (ret!=null) ret.setAnswer(answerStr);
        return ret;
    }
}
