package io.github.dwarfley.gradle.versioncompanion.git;

import java.nio.file.Path;
import java.util.*;

import org.gradle.api.*;

import io.github.dwarfley.gradle.versioncompanion.util.*;

public final class Git {
	
	private static Map<Project, GitRepository> mRepos = new HashMap<>();
	
	public static boolean isInstalled(){
		return getVersion() != null;
	}
	
	public static String getVersion(){
		return Executor.exec(new String[]{
			"git", "--version"
		});
	}
	
	public static GitRepository getRepository(Project pProject){
		
		if(!Git.isInstalled()){
			throw new GradleException("The git command is not installed!");
		}
		
		GitRepository lRepo = mRepos.get(pProject);
		
		if(lRepo == null){
			
			Path lProjectPath = PathUtils.getProjectPath(pProject);
			
			if(isRepo(lProjectPath)){
				lRepo = new GitRepository(lProjectPath);
			}else if(pProject != pProject.getRootProject()){
				lRepo = getRepository(pProject.getParent());
			}else{
				throw new GradleException("The project is not part of a valid git repository!");
			}
			
			mRepos.put(pProject, lRepo);
			
		}
		
		return lRepo;
	}
	
	private static boolean isRepo(Path pRootDir){
		
		String lGitDir = Executor.exec(pRootDir, new String[]{
			"git", "rev-parse", "--show-toplevel"
		});
		
		if(lGitDir == null){
			return false;
		}
		
		Path lGitPath = PathUtils.getFullPath(Path.of(lGitDir));
		
		return pRootDir.equals(lGitPath);
	}
	
	private Git(){}
	
}
