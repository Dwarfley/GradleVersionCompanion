package io.github.dwarfley.gradle.versioncompanion.tasks;

import org.gradle.api.Project;

import io.github.dwarfley.gradle.versioncompanion.*;
import io.github.dwarfley.gradle.versioncompanion.git.GitRepository;
import io.github.dwarfley.gradle.versioncompanion.version.*;

public class CurrentVersionTask extends VersionCompanionDefaultTask {
	
	@Override
	protected void execTask(Project pProject, VersionCompanionExtension pExt, GitRepository pRepo){
		
		FullVersion lVersion = VersionUtils.getCurrent(pExt, pRepo);
		
		System.out.println("Current version: v" + lVersion.toString());
		
	}
	
}
