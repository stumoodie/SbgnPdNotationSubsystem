package org.pathwayeditor.notations.sbgnpd.services;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class NotationResources {
	private static final String BUNDLE_NAME = "org.pathwayeditor.notations.sbgnpd.services.notation"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private NotationResources() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
