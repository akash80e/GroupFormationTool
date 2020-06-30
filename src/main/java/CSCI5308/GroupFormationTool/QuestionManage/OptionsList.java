package CSCI5308.GroupFormationTool.QuestionManage;

import java.util.ArrayList;
import java.util.List;

public class OptionsList {

	public static final int NO_OF_OPTIONS = 5;
	public List<Options> options = new ArrayList<Options>();

	public List<Options> getOptions()
	{
		return options;
	}

	public void setOptions(List<Options> options)
	{
		this.options = options;
	}

	
	public void add()
	{
		for (int i = 1; i <= NO_OF_OPTIONS; i++) {
			this.options.add(new Options(i));
		}
	}

	public boolean insertOptions(IQuestionPersistence questionDB, OptionsList options)
	{
		for (int i = 0; i < NO_OF_OPTIONS; i++) {
			Options option = new Options();
			option.setDescription(options.getOptions().get(i).getDescription());
			option.setStoredAs(options.getOptions().get(i).getStoredAs());
			if(isStringEmpty(option.getDescription())){
				continue;
			}
			questionDB.insertOptions(option);
		}
		return true;
	}

	public boolean isStringEmpty(String s)
	{
		return s.replaceAll(" ","").length() == 0;
	}

}
