package io.github.dwarfley.gradle.versioncompanion.version;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import io.github.dwarfley.gradle.versioncompanion.VersionCompanionExtension;
import io.github.dwarfley.gradle.versioncompanion.git.GitRepository;

public final class VersionUtils {
	
	public static List<FullVersion> getAll(VersionCompanionExtension pExt, GitRepository pRepo){
		
		List<FullVersion> lResult = new ArrayList<>();
		
		List<String> lTags = pRepo.getAllTags();
		List<Version> lVersions = parseAllVersions(pExt, lTags);
		
		VersionSorter.sort(lVersions);
		
		for(Version lVersion : lVersions){
			
			if(lVersion instanceof FullVersion lFullVersion){
				lResult.add(lFullVersion);
			}
			
		}
		
		return lResult;
	}
	
	public static FullVersion getCurrent(VersionCompanionExtension pExt, GitRepository pRepo){
		
		FullVersion lResult;
		
		Version lVersion;
		
		boolean lIsDev = true;
		int lDepth = 0;
		
		lVersion = parseLatestVersion(pExt, pRepo.getCurrentTags());
		
		if(lVersion != null){
			
			if(lVersion instanceof FullVersion lFullVersion){
				lIsDev = false;
				lResult = lFullVersion;
			}else{
				lDepth = pRepo.getTagDepth(getTagName(pExt, lVersion));
				lResult = ((ShortVersion) lVersion).getFirstVersion();
			}
			
		}else{
			
			lVersion = parseLatestVersion(pExt, pRepo.getReachableTags());
			
			if(lVersion != null){
				
				if(lVersion instanceof FullVersion lFullVersion){
					lDepth = pRepo.getTagDepth(getTagName(pExt, lVersion));
					lResult = lFullVersion;
					if(lResult.getReleaseInfo().size() == 0 && lResult.getBuildInfo().size() == 0){
						lResult.incPatch();
					}
				}else{
					lDepth = pRepo.getTagDepth(getTagName(pExt, lVersion));
					lResult = ((ShortVersion) lVersion).getFirstVersion();
				}
				
			}else{
				
				lDepth = pRepo.getCommitCount();
				lResult = new FullVersion(0, 1, 0);
				
			}
			
		}
		
		if(lIsDev){
			
			lResult.getReleaseInfo().clear();
			lResult.getBuildInfo().clear();
			
			String lDevString = "dev";
			
			if(pExt.useDepth){
				lDevString += String.format("%03d", lDepth);
			}
			
			lResult.getReleaseInfo().add(lDevString);
			
			DateTimeFormatter lFormatter = DateTimeFormatter.ofPattern(pExt.timestampFormat);
			
			if(pExt.useTimestamp){
				lResult.getBuildInfo().add(lFormatter.format(LocalDateTime.now()));
			}
			
		}
		
		if(pExt.useDirty && pRepo.isDirty()){
			lResult.getReleaseInfo().add("dirty");
		}
		
		return lResult;
	}
	
	private static Version parseLatestVersion(VersionCompanionExtension pExt, List<String> pTags){
		
		List<Version> lVersions = parseAllVersions(pExt, pTags);
		
		if(lVersions.size() < 1){
			return null;
		}
		
		VersionSorter.sort(lVersions);
		
		return lVersions.get(lVersions.size() - 1);
	}
	
	private static List<Version> parseAllVersions(VersionCompanionExtension pExt, List<String> pTags){
		
		List<Version> lVersions = new ArrayList<>();
		
		for(String lTag : pTags){
			
			if(!lTag.startsWith(pExt.tagPrefix) || !lTag.endsWith(pExt.tagSuffix)){
				continue;
			}
			
			int lStart = pExt.tagPrefix.length();
			int lEnd = lTag.length() - pExt.tagSuffix.length();
			
			Version lVersion = VersionParser.parse(lTag.substring(lStart, lEnd));
			
			if(lVersion != null){
				lVersions.add(lVersion);
			}
			
		}
		
		return lVersions;
	}
	
	private static String getTagName(VersionCompanionExtension pExt, Version pVersion){
		return pExt.tagPrefix + pVersion.toString() + pExt.tagSuffix;
	}
	
	private VersionUtils(){}
	
}
