package io.github.dwarfley.gradle.versioncompanion;

import java.time.format.DateTimeFormatter;

import org.gradle.api.GradleException;

public class VersionCompanionExtension {
	
	public String tagPrefix;
	public String tagSuffix;
	
	public boolean useDirty;
	public boolean useDepth;
	public boolean useTimestamp;
	
	public String timestampFormat;
	
	void setToDefaults(){
		
		tagPrefix = "v";
		tagSuffix = "";
		
		useDirty = true;
		useDepth = true;
		useTimestamp = false;
		
		timestampFormat = "yyMMdd.HHmm";
		
	}
	
	void validate(){
		
		if(tagPrefix == null){
			throw new GradleException("Tag prefix must not be null!");
		}
		
		if(tagSuffix == null){
			throw new GradleException("Tag suffix must not be null!");
		}
		
		if(timestampFormat == null){
			throw new GradleException("Timestamp format must not be null!");
		}
		
		try{
			DateTimeFormatter.ofPattern(timestampFormat);
		}catch(Exception lEx){
			throw new GradleException("Timestamp format is invalid!");
		}
		
	}
	
}
