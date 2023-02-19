package io.github.dwarfley.gradle.versioncompanion.tasks;

import java.util.List;

import org.gradle.api.Project;

import io.github.dwarfley.gradle.versioncompanion.*;
import io.github.dwarfley.gradle.versioncompanion.git.GitRepository;
import io.github.dwarfley.gradle.versioncompanion.version.*;

public class AllVersionsTask extends VersionCompanionDefaultTask {
	
	@Override
	protected void execTask(Project pProject, VersionCompanionExtension pExt, GitRepository pRepo){
		
		String lPadding = " ".repeat(4);
		
		System.out.println("Versions:");
		
		List<FullVersion> lVersions = VersionUtils.getAll(pExt, pRepo);
		
		for(FullVersion lVersion : lVersions){
			System.out.println(lPadding + "v" + lVersion.toString());
		}
		
	}
	
}
