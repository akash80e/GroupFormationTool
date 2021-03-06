package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Question implements IQuestion
{
	private long id;
	private String title;
	private String text;
	private QuestionType type;
	private Timestamp timestamp;
	private ArrayList<String> options;
	
	public Question() 
	{
		setDefaults();
	}
	
	public void setDefaults() 
	{
		id=-1;
		title="";
		text="";
		type=null;
		timestamp=null;
	}
	
	public Timestamp getTimestamp() 
	{
		return timestamp;
	}
	
	public void setTimestamp(Timestamp timestamp) 
	{
		this.timestamp = timestamp;
	}
	
	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getText() 
	{
		return text;
	}
	
	public void setText(String text) 
	{
		this.text = text;
	}
	
	public QuestionType getType() 
	{
		return type;
	}
	
	public void setType(QuestionType type) 
	{
		this.type = type;
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<String> options) {
		this.options = options;
	}

	public boolean deleteQuestion(IQuestionPersistence questionDB, long questionID) 
	{
		return questionDB.deleteQuestionByQuestionId(questionID);
	}
	
	public long createQuestion(IQuestionPersistence questionDB, String bannerID) 
	{
		
		return questionDB.createQuestion(this, bannerID);
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", text=" + text + ", type=" + type + ", timestamp="
				+ timestamp + ", options=" + options + "]";
	}

}
