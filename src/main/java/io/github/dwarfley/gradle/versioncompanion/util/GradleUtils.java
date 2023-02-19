package io.github.dwarfley.gradle.versioncompanion.util;

import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

import io.github.dwarfley.gradle.versioncompanion.VersionCompanionExtension;

public final class GradleUtils {
	
	public static VersionCompanionExtension getExtension(Project pProject){
		
		ExtensionContainer lExtensions = pProject.getExtensions();
		VersionCompanionExtension lExt = lExtensions.getByType(VersionCompanionExtension.class);
		
		return lExt;
	}
	
	private GradleUtils(){}
	
}
