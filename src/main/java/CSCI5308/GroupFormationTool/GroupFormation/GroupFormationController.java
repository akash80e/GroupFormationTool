package CSCI5308.GroupFormationTool.GroupFormation;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CSCI5308.GroupFormationTool.Survey.ISurveyManagePersistence;
import CSCI5308.GroupFormationTool.Survey.SurveyAbstractFactory;

@Controller
public class GroupFormationController {

	private static final String courseID = "courseID";
	private ISurveyManagePersistence surveyManageDB;
	private IGroupCreator groupCreator;
	private IGroupsPersistence groupDB;
	private static final Logger LOG = LogManager.getLogger();

	public GroupFormationController() {
		surveyManageDB = SurveyAbstractFactory.instance().createSurveyManageDBInstance();
		groupDB = GroupsAbstractFactory.instance().createGroupsDBInstance();
		groupCreator = GroupsAbstractFactory.instance().createDefaultGroupCreator();

	}

	@GetMapping("/formgroups")
	public String loadGroups(Model model, @RequestParam(name = courseID) long courseId) {
		long surveyId = surveyManageDB.findSurveyByCourseID(courseId);
		IGroups groups = GroupsAbstractFactory.instance().createGroupsInstance();
		boolean check = false;
		if (Objects.isNull(surveyId)) {
			LOG.error("Survey not created for SurveyId:" + surveyId);
			model.addAttribute("errorMsg", "Survey not created");
		} else {
			LOG.info("check if published or not");
			check = surveyManageDB.surveyPublishedOrNot(courseId);
		}
		if (check) {
			LOG.error("Survey is  Not Published for SurveyId:" + surveyId);
			model.addAttribute("errorMsg", "Survey not published");
			return "groupFormation/notpublished";

		} else {

			groups.insertGroups(groupDB, surveyId, groupCreator, surveyManageDB);
			Map<Integer, ArrayList<IGroups>> groupInfo = groups.fetchGroups(groupDB);
			LOG.info("Survey published and groups created for SurveyId:" + surveyId);
			model.addAttribute("groupInfo", groupInfo);
		}

		return "groupFormation/group";
	}

}
