package io.github.dwarfley.gradle.versioncompanion;

import org.gradle.api.*;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.*;

import io.github.dwarfley.gradle.versioncompanion.git.Git;
import io.github.dwarfley.gradle.versioncompanion.tasks.CurrentVersionTask;
import io.github.dwarfley.gradle.versioncompanion.util.GradleUtils;
import io.github.dwarfley.gradle.versioncompanion.version.*;

public class VersionCompanionPlugin implements Plugin<Project> {
	
	@Override
	public void apply(Project pProject){
		
		createExtension(pProject);
		addTasks(pProject);
		setVersion(pProject);
		
	}
	
	private void createExtension(Project pProject){
		
		ExtensionContainer lExtensions = pProject.getExtensions();
		
		VersionCompanionExtension lExt = lExtensions.create("versionCompanion", VersionCompanionExtension.class);
		lExt.setToDefaults();
		
	}
	
	private void addTasks(Project pProject){
		
		TaskContainer lTasks = pProject.getTasks();
		
		addTask(lTasks, "currentVersion", CurrentVersionTask.class);
		addTask(lTasks, "allVersions", CurrentVersionTask.class);
		
	}
	
	private void addTask(TaskContainer pTasks, String pName, Class<? extends VersionCompanionDefaultTask> pClass){
		
		TaskProvider<? extends VersionCompanionDefaultTask> lProvider = pTasks.register(pName, pClass);
		
		lProvider.configure(pTask -> {
			pTask.setGroup("VersionCompanion");
		});
		
	}
	
	private void setVersion(Project pProject){
		
		VersionCompanionExtension lExt = GradleUtils.getExtension(pProject);
		
		FullVersion lCurrentVersion = VersionUtils.getCurrent(lExt, Git.getRepository(pProject));
		pProject.setVersion(lCurrentVersion.toString());
		
	}
	
}
