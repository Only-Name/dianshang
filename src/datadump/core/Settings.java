package datadump.core;

import datadump.exception.ConfigParamException;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * 参数设置类
 */
public final class Settings {
	private static final Settings settings = new Settings();
	private Properties conf;
	//初始化
	private boolean initialized = false;

	private String xsd_dir;
	private String hostname;
	private String username;
	private String password;
	private String softName;
	private String xmlPath;
	private int ThreadNum;
	private int port;


	public static synchronized void initial(Properties properties) throws ConfigParamException {
		if (!settings.initialized) {
			settings.initialized = true;
			settings.conf = properties;

			settings.xsd_dir = settings.getString("xsd_dir");
			settings.hostname = settings.getString("hostname");
			settings.port = settings.getInt("port");
			settings.ThreadNum = settings.getInt("ThreadNum");
			settings.username = settings.getString("username");
			settings.password = settings.getString("password");
			settings.softName = settings.getString("softName");
			settings.xmlPath = settings.getString("xmlPath");

		}
	}

	public static boolean isInitialized() {
		return settings.initialized;
	}

	private String getString(String key) throws ConfigParamException {
		String value = this.conf.getProperty(key);
		if (value == null) {
			throw new ConfigParamException(String.format("系统配置参数%1$s丢失", new Object[]{key}));
		}
		return value;
	}

	private boolean getBoolean(String key) throws ConfigParamException {
		String value = getString(key);
		if ((!value.equalsIgnoreCase("YES")) && (!value.equalsIgnoreCase("NO"))) {
			throw new ConfigParamException(String.format("系统配置参数%1$s错误,可选项:YES/NO,实际值:%2$s", new Object[]{key, value}));
		}
		return value.equalsIgnoreCase("YES");
	}

	private int getInt(String key) throws ConfigParamException {
		String value = getString(key);
		Pattern p = Pattern.compile("^[0-9]{1,9}$");
		if (!p.matcher(value).find()) {
			throw new ConfigParamException(String.format("系统配置参数%1$s错误,请输入正整数,实际值:%2$s", new Object[]{key, value}));
		}
		return Integer.parseInt(value);
	}

	private double getDouble(String key) throws ConfigParamException {
		String value = getString(key);
		Pattern p = Pattern.compile("^[0-9]+[.]{0,1}[0,9]+$");
		if (!p.matcher(value).find()) {
			throw new ConfigParamException(String.format("系统配置参数%1$s错误,请输入浮点数,实际值:%2$s", new Object[]{key, value}));
		}
		return Double.parseDouble(value);
	}

	private long getLong(String key) throws ConfigParamException {
		String value = getString(key);
		Pattern p = Pattern.compile("^[0-9]{1,18}$");
		if (!p.matcher(value).find()) {
			throw new ConfigParamException(String.format("系统配置参数%1$s错误,请输入正长整数,实际值:%2$s", new Object[]{key, value}));
		}
		return Long.parseLong(value);
	}

	public static String getXsd_dir() {
		return settings.xsd_dir;
	}

	public  static String getHostname() {
		return settings.hostname;
	}

	public static String getUsername() {
		return settings.username;
	}

	public static String getPassword() {
		return settings.password;
	}

	public static int getPort() {
		return settings.port;
	}

	public static int getThreadNum() {
		return settings.ThreadNum;
	}

	public static String getSoftName() {
		return settings.softName;
	}

	public static String getXmlPath() {
		return settings.xmlPath;
	}
}
