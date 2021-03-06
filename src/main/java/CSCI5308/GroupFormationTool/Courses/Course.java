package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;

public class Course implements ICourse {
	private long id;
	private String title;
	private ICourseUserRelationship userRoleDecider;
	private static final Logger LOG = LogManager.getLogger();

	public Course() {
		setDefaults();
	}

	public Course(long id, ICoursePersistence courseDB) {
		setDefaults();
		courseDB.loadCourseByID(id, this);
	}

	public void setDefaults() {
		id = -1;
		title = "";
		userRoleDecider = CourseAbstractFactory.instance().courseUserInstance();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean delete(ICoursePersistence courseDB) {
		return courseDB.deleteCourse(id);
	}

	public boolean createCourse(ICoursePersistence courseDB) {
		return courseDB.createCourse(this);
	}

	public boolean enrollUserInCourse(Role role, IUser user) {
		LOG.info("Calling enrollUserInCourse to enroll user: " + user.getBannerID());
		return userRoleDecider.enrollUserInCourse(user, this, role);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(Role role) {
		LOG.info("Checking if  user has Role :" + role + " for the given course");
		return userRoleDecider.userHasRoleInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), role, this);
	}

	public boolean isCurrentUserEnrolledAsRoleInCourse(String role) {
		Role r = Role.valueOf(role.toUpperCase());
		LOG.info("Checking if  user has Role :" + role + " for the given course");
		return isCurrentUserEnrolledAsRoleInCourse(r);
	}

	public List<Role> getAllRolesForCurrentUserInCourse() {
		LOG.info("Get all Roles for User in course");
		return userRoleDecider.loadAllRoluesForUserInCourse(CurrentUser.instance().getCurrentAuthenticatedUser(), this);
	}
}
