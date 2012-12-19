package me.config;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringApplicationContextInitializer implements
		ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {

	private CloudEnvironment cloudEnvironment = new CloudEnvironment();

	private boolean isCloudFoundry() {
		return cloudEnvironment.isCloudFoundry();
	}

	private boolean isAppFog() {
		if (!isCloudFoundry())
			return false;
		String cloudApiUri = cloudEnvironment.getCloudApiUri().toLowerCase();
		return (cloudApiUri.contains(".af"));
	}

	private boolean isLocal() {
		return !isCloudFoundry();
	}

	public void initialize(
			AnnotationConfigWebApplicationContext applicationContext) {

		String profile = "default";
		if (isAppFog()) {
			profile = "appfog";
		} else if (isCloudFoundry()) {
			profile = "cloud";
		}
		applicationContext.getEnvironment().setActiveProfiles(profile);

		Class<?>[] configs = { SpringConfiguration.class };

		String[] basePkgs = new String[configs.length];
		int i = 0;
		for (Class<?> pkg : configs)
			basePkgs[i++] = pkg.getPackage().getName();

		applicationContext.scan(basePkgs);
		applicationContext.refresh();
	}
}