package io.github.dwarfley.gradle.versioncompanion.util;

import java.io.File;
import java.nio.file.Path;

import org.gradle.api.Project;

public final class PathUtils {
	
	public static Path getProjectPath(Project pProject){
		return getFullPath(pProject.getProjectDir());
	}
	
	public static Path getFullPath(File pFile){
		return getFullPath(pFile.toPath());
	}
	
	public static Path getFullPath(Path pPath){
		return pPath.toAbsolutePath().normalize();
	}
	
	private PathUtils(){}
	
}
