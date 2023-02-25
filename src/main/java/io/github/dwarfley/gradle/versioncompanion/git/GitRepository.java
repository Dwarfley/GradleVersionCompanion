package io.github.dwarfley.gradle.versioncompanion.git;

import java.nio.file.Path;
import java.util.List;

import org.gradle.api.GradleException;

import io.github.dwarfley.gradle.versioncompanion.util.Executor;

public final class GitRepository {
	
	private Path mRootDir;
	
	GitRepository(Path pRootDir){
		mRootDir = pRootDir;
	}
	
	public int getTagDepth(String pTag){
		
		String lDescription = Executor.exec(mRootDir, new String[] {
			"git",
			"describe",
			"--match",
			pTag
		});
		
		if(lDescription == null){
			throw new GradleException("Failed to fetch tag depth!");
		}
		
		if(lDescription.equals(pTag)){
			return 0;
		}
		
		String lDepth = lDescription.substring(pTag.length() + 1).split("-")[0];
		
		return Integer.parseInt(lDepth);
	}
	
	public int getCommitCount(){
		
		String lCount = Executor.exec(mRootDir, new String[] {
			"git",
			"rev-list",
			"--first-parent",
			"--count",
			"HEAD"
		});
		
		if(lCount == null){
			throw new GradleException("Failed to fetch commit count!");
		}
		
		return Integer.parseInt(lCount);
	}
	
	public List<String> getCurrentTags(){
		
		List<String> lTags = Executor.execLines(mRootDir, new String[] {
			"git",
			"tag",
			"--points-at",
			"HEAD"
		});
		
		if(lTags == null){
			throw new GradleException("Failed to fetch current tags!");
		}
		
		return lTags;
	}
	
	public List<String> getReachableTags(){
		
		List<String> lTags = Executor.execLines(mRootDir, new String[] {
			"git",
			"tag",
			"--merged"
		});
		
		if(lTags == null){
			throw new GradleException("Failed to fetch reachable tags!");
		}
		
		return lTags;
	}
	
	public List<String> getAllTags(){
		
		List<String> lTags = Executor.execLines(mRootDir, new String[] {
			"git",
			"tag"
		});
		
		if(lTags == null){
			throw new GradleException("Failed to fetch all tags!");
		}
		
		return lTags;
	}
	
	public boolean isEmpty(){
		
		List<String> lResult = Executor.execLines(mRootDir, new String[] {
			"git",
			"rev-parse",
			"HEAD"
		});
		
		return lResult == null;
	}
	
	public boolean isDirty(){
		
		List<String> lStatus = Executor.execLines(mRootDir, new String[] {
			"git",
			"status",
			"--porcelain"
		});
		
		if(lStatus == null){
			throw new GradleException("Failed to fetch status!");
		}
		
		return lStatus.size() > 0;
	}
	
}
