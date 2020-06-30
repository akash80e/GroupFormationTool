package CSCI5308.GroupFormationTool.Security;

import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.SystemConfig;

import java.util.List;

public class PasswordSecurityPolicy implements IPasswordSecurityPolicy {

	private static String MIN_LENGTH;
	private static String MIN_LENGTH_ENABLED;
	private static String MAX_LENGTH;
	private static String MAX_LENGTH_ENABLED;
	private static String MIN_UPPERCASE_CHARS;
	private static String MIN_UPPERCASE_CHARS_ENABLED;
	private static String MIN_LOWERCASE_CHARS;
	private static String MIN_LOWERCASE_CHARS_ENABLED;
	private static String MIN_SPECIAL_CHARS;
	private static String MIN_SPECIAL_CHARS_ENABLED;
	private static String CHARS_NOT_ALLOWED;
	private static String CHARS_NOT_ALLOWED_ENABLED;
	private static String PASSWORD_HISTORY_COUNT;
	private static String PASSWORD_HISTORY_ENABLED;

	private IPasswordSecurityPolicyConfig passwordSecurityPolicyConfig;

	private IPasswordManager passwordManager;

	public PasswordSecurityPolicy(IPasswordManager passwordManager, IPasswordSecurityPolicyConfig passwordSecurityPolicyConfig){
		this.passwordManager = passwordManager;
		this.passwordSecurityPolicyConfig =  passwordSecurityPolicyConfig;
	}

	public String isFollowingSecurityRules(String password) {

		MIN_LENGTH = passwordSecurityPolicyConfig.getMinLength();
		MIN_LENGTH_ENABLED = passwordSecurityPolicyConfig.getMinLengthEnabled();
		MAX_LENGTH = passwordSecurityPolicyConfig.getMaxLength();
		MAX_LENGTH_ENABLED = passwordSecurityPolicyConfig.getMaxLengthEnabled();
		MIN_UPPERCASE_CHARS = passwordSecurityPolicyConfig.getMinUppercaseChars();
		MIN_UPPERCASE_CHARS_ENABLED = passwordSecurityPolicyConfig.getMinUppercaseCharsEnabled();
		MIN_LOWERCASE_CHARS = passwordSecurityPolicyConfig.getMinLowercaseChars();
		MIN_LOWERCASE_CHARS_ENABLED = passwordSecurityPolicyConfig.getMinLowercaseCharsEnabled();
		MIN_SPECIAL_CHARS = passwordSecurityPolicyConfig.getMinSpecialChars();
		MIN_SPECIAL_CHARS_ENABLED = passwordSecurityPolicyConfig.getMinSpecialCharsEnabled();
		CHARS_NOT_ALLOWED = passwordSecurityPolicyConfig.getCharsNotAllowed();
		CHARS_NOT_ALLOWED_ENABLED = passwordSecurityPolicyConfig.getCharsNotAllowedEnabled();

		int upperCaseCount = 0;
		int lowerCaseCount = 0;
		int specialCharCount = 0;
		for (int i = 0; i < password.length(); i++) {
			if (Character.isUpperCase(password.charAt(i))) {
				upperCaseCount++;
			}
			if (Character.isLowerCase(password.charAt(i))) {
				lowerCaseCount++;
			}
		}
		for (int i = 1; i <= password.length(); i++) {
			if (password.substring(i - 1, i).matches("[^A-Za-z0-9 ]")) {
				specialCharCount++;
			}
		}
		
		if ((MIN_LENGTH_ENABLED == null) || (MAX_LENGTH_ENABLED == null) || (MIN_UPPERCASE_CHARS_ENABLED == null)
				|| (MIN_LOWERCASE_CHARS_ENABLED == null)|| (MIN_SPECIAL_CHARS_ENABLED == null)|| (CHARS_NOT_ALLOWED_ENABLED == null)) {
			return "Password security policy not found!!";
		}
		

		if (Integer.parseInt(MIN_LENGTH_ENABLED) == 1) {
			if (password.length() < Integer.parseInt(MIN_LENGTH)) {
				return "Minimum password length required : " + MIN_LENGTH;
			}
		}
		if (Integer.parseInt(MAX_LENGTH_ENABLED) == 1) {
			if (password.length() > Integer.parseInt(MAX_LENGTH)) {
				return "Maximum password length allowed: " + MAX_LENGTH;
			}
		}
		if (Integer.parseInt(MIN_UPPERCASE_CHARS_ENABLED) == 1) {

			if (upperCaseCount < Integer.parseInt(MIN_UPPERCASE_CHARS)) {
				return "Minimum uppercase characters required in password : " + MIN_UPPERCASE_CHARS;
			}
		}
		if (Integer.parseInt(MIN_LOWERCASE_CHARS_ENABLED) == 1) {

			if (lowerCaseCount < Integer.parseInt(MIN_LOWERCASE_CHARS)) {
				return "Minimum lowercase characters required in password : " + MIN_LOWERCASE_CHARS;
			}
		}
		if (Integer.parseInt(MIN_SPECIAL_CHARS_ENABLED) == 1) {

			if (specialCharCount < Integer.parseInt(MIN_SPECIAL_CHARS)) {
				return "Minimum special characters required : " + MIN_SPECIAL_CHARS;
			}
		}
		if (Integer.parseInt(CHARS_NOT_ALLOWED_ENABLED) == 1) {
		
			for(int i=0;i<CHARS_NOT_ALLOWED.length();i++) {
				if(password!=null && password.indexOf(CHARS_NOT_ALLOWED.charAt(i)) >= 0) {
				return "Characters not allowed in password : " + CHARS_NOT_ALLOWED;
			}

		}
		}

		return null;
	}

	@Override
	public boolean checkPreviousPassword(User U, String password) {

		PASSWORD_HISTORY_ENABLED = passwordSecurityPolicyConfig.getPasswordHistoryEnabled();
		PASSWORD_HISTORY_COUNT = passwordSecurityPolicyConfig.getPasswordHistoryCount();

		if (Integer.parseInt(PASSWORD_HISTORY_ENABLED) == 0){
			return true;
		}

		List<String> previousPasswords = passwordManager.getPreviousPasswords(U, Integer.parseInt(PASSWORD_HISTORY_COUNT));
		System.out.println(previousPasswords);
		System.out.println(password);
		IPasswordEncryption passwordEncryption = SystemConfig.instance().getPasswordEncryption();

		for(int i=0;i<previousPasswords.size();i++){
			if (passwordEncryption.matches(password, previousPasswords.get(i)))
			{
				return false;
			}
		}
		return true;
	}

}