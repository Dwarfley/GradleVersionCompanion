package io.github.dwarfley.gradle.versioncompanion;

import org.gradle.api.*;
import org.gradle.api.tasks.TaskAction;

import io.github.dwarfley.gradle.versioncompanion.git.*;
import io.github.dwarfley.gradle.versioncompanion.util.GradleUtils;

public abstract class VersionCompanionDefaultTask extends DefaultTask {
	
	protected abstract void execTask(Project pProject, VersionCompanionExtension pExt, GitRepository pRepo);
	
	@TaskAction
	private void exec(){
		
		Project lProject = this.getProject();
		
		VersionCompanionExtension lExt = GradleUtils.getExtension(lProject);
		lExt.validate();
		
		execTask(lProject, lExt, Git.getRepository(lProject));
		
	}
	
}
