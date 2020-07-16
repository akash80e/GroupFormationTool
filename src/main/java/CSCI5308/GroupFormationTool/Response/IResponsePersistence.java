package CSCI5308.GroupFormationTool.Response;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IResponsePersistence {

	public List<IQuestion> loadQuestionsWithoutOptions(long courseId);

	public List<IQuestion> loadQuestionsWithOptions(long courseId);

	public List<IQuestion> loadQuestionsOptions(List<IQuestion> quesions);

	public boolean saveResponse(HashMap<String, String> answer, String bannerId) throws SQLException;

	public boolean checkIsMCQMultiple(String questionId);

}
