package CSCI5308.GroupFormationTool.Database;

public class DefaultDatabaseConfiguration implements IDatabaseConfiguration
{
	private static final String URL = "jdbc:mysql://localhost:3306/M2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";//System.getenv("USER");
	private static final String PASSWORD = "password";//System.getenv("PASSWORD");

	/*
	* private static String dbURL = "
	private static String dbUserName = "CSCI5308_21_PRODUCTION_USER";
	private static String dbPassword = "CSCI5308_21_PRODUCTION_21798";*/


	public String getDatabaseUserName()
	{
		return USER;
	}

	public String getDatabasePassword()
	{
		return PASSWORD;
	}

	public String getDatabaseURL()
	{
		return URL;
	}
}
