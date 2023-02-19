package io.github.dwarfley.gradle.versioncompanion.version;

import java.util.*;

public sealed abstract class Version permits ShortVersion, FullVersion {
	
	private List<String> mReleaseInfo;
	private List<String> mBuildInfo;
	
	Version(){
		mReleaseInfo = new ArrayList<>();
		mBuildInfo = new ArrayList<>();
	}
	
	public List<String> getReleaseInfo(){
		return mReleaseInfo;
	}
	
	public List<String> getBuildInfo(){
		return mBuildInfo;
	}
	
	@Override
	public boolean equals(Object pObject){
		if(pObject instanceof Version lVersion){
			return mReleaseInfo.equals(lVersion.mReleaseInfo) && mBuildInfo.equals(lVersion.mBuildInfo);
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return mReleaseInfo.hashCode() * mBuildInfo.hashCode();
	}
	
	@Override
	public String toString(){
		
		String lReleaseInfo = String.join(".", mReleaseInfo);
		String lBuildInfo = String.join(".", mBuildInfo);
		
		String lResult = "";
		
		if(lReleaseInfo.length() > 0){
			lResult += "-" + lReleaseInfo;
		}
		
		if(lBuildInfo.length() > 0){
			lResult += "+" + lBuildInfo;
		}
		
		return lResult;
	}
	
}
