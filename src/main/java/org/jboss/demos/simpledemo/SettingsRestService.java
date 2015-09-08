package org.jboss.demos.simpledemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class SettingsRestService {

	private Map<String, String> settingsMap = new HashMap<String, String>();

	public SettingsRestService() throws InterruptedException, IOException {
		settingsMap.put("hostname", getHostname());
		settingsMap.put("os", getOperatingSystemInfo());
		settingsMap.put("username", System.getProperty("user.name"));
		settingsMap.put("homedir", System.getProperty("user.home"));
		settingsMap.put("workingdir", System.getProperty("user.dir"));
		settingsMap.put("java_home", System.getProperty("java.home"));
		settingsMap.put("java_vendor", System.getProperty("java.vendor"));
		settingsMap.put("java_version", System.getProperty("java.version"));
	}

	@GET
	@Path("settings")
	@Produces({ "application/json" })
	public Map<String, String> getSettings() throws IOException {
		return settingsMap;
	}

	private String getHostname() throws InterruptedException, IOException {
		String OS = System.getProperty("os.name");
		if (OS.indexOf("win") >= 0) {
			return System.getenv("COMPUTERNAME");
		} else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0
				|| OS.indexOf("Mac OS") >= 0) {
			if (Runtime.getRuntime().exec("test -f /.dockerinit").waitFor() == 0) {
				// uname -a | awk '{print $2}'
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(Runtime.getRuntime()
								.exec("uname -a").getInputStream()));
				return reader.readLine().split("\\s")[1];
			} else {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(Runtime.getRuntime()
								.exec("hostname").getInputStream()));
				return reader.readLine();
			}
		} else {
			return "Unknown";
		}
	}

	private String getOperatingSystemInfo() throws InterruptedException,
			IOException {
		String OS = System.getProperty("os.name");
		if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0
				|| OS.indexOf("Mac OS") >= 0) {
			if (Runtime.getRuntime().exec("test -f /etc/redhat-release")
					.waitFor() == 0) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(Runtime.getRuntime()
								.exec("cat /etc/redhat-release")
								.getInputStream()));
				return reader.readLine();
			} else {
				return String.format("%1$s-%3$s (%2$s)",
						System.getProperty("os.name"),
						System.getProperty("os.arch"),
						System.getProperty("os.version"));
			}
		}
		return "UNKNOWN";
	}
}
