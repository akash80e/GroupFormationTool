package CSCI5308.GroupFormationTool.AccessControl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import CSCI5308.GroupFormationTool.PasswordValidation.IPasswordValidatorEnumerator;
import CSCI5308.GroupFormationTool.PasswordValidation.PasswordValidator;
import CSCI5308.GroupFormationTool.Security.IPasswordEncryption;

public class User implements IUser {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	private static final Logger LOG = LogManager.getLogger();
	private long id;
	private String password;
	private String bannerID;
	private String firstName;
	private String lastName;
	private String email;

	public User() {
		setDefaults();
	}

	public User(long id, IUserPersistence persistence) {
		setDefaults();
		persistence.loadUserByID(id, this);
	}

	public User(String bannerID, IUserPersistence persistence) {
		setDefaults();
		persistence.loadUserByBannerID(bannerID, this);
	}

	@Override
	public void setDefaults() {
		id = -1;
		password = "";
		bannerID = "";
		firstName = "";
		lastName = "";
		email = "";
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setBannerID(String bannerID) {
		this.bannerID = bannerID;
	}

	@Override
	public String getBannerID() {
		return bannerID;
	}

	@Override
	public String getBanner() {
		return bannerID;
	}

	@Override
	public void setFirstName(String name) {
		firstName = name;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setLastName(String name) {
		lastName = name;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public boolean isValidUser() {
		return id != -1;
	}

	@Override
	public boolean isInValidUser() {
		return id == -1;
	}

	@Override
	public boolean createUser(IUserPersistence userDB, IPasswordValidatorEnumerator passwordEnumerator,
			IPasswordEncryption passwordEncryption, IUserNotifications notification, List<String> errorMessages) {
		String rawPassword = password;
		boolean success = true;

		List<PasswordValidator> passwordValidators = passwordEnumerator.getActiveValidators(this);
		for (int i = 0; i < passwordValidators.size(); i++) {
			LOG.info("checking for password security policies validation");
			PasswordValidator validator = passwordValidators.get(i);
			if (validator.isValid(rawPassword) == false) {
				LOG.error("User cannot be created due to violation of password security policy");
				errorMessages.add(validator.getValidatorName() + " - " + validator.constraint);
				success = false;
			}
		}
		if (success) {
			success = this.createUser(userDB, passwordEncryption, notification);
		}
		return success;
	}

	@Override
	public boolean createUser(IUserPersistence userDB, IPasswordEncryption passwordEncryption,
			IUserNotifications notification) {
		String rawPassword = password;
		this.password = passwordEncryption.encryptPassword(this.password);
		LOG.info("After validating user, passing details to UserDB for user creation");
		boolean success = userDB.createUser(this);
		if (success && (null != notification)) {
			notification.sendUserLoginCredentials(this, rawPassword);
		}
		return success;
	}

	@Override
	public boolean updateUser(IUserPersistence userDB) {
		LOG.info("Calling UserDB to update user details");
		return userDB.updateUser(this);
	}

	@Override
	public boolean isStringNullOrEmpty(String s) {
		if (null == s) {
			return true;
		}
		return s.isEmpty();
	}

	@Override
	public boolean isBannerIDValid(String bannerID) {
		return !isStringNullOrEmpty(bannerID);
	}

	@Override
	public boolean isFirstNameValid(String name) {
		return !isStringNullOrEmpty(name);
	}

	@Override
	public boolean isLastNameValid(String name) {
		return !isStringNullOrEmpty(name);
	}

	@Override
	public boolean isEmailValid(String email) {
		if (isStringNullOrEmpty(email)) {

			return false;
		}

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}