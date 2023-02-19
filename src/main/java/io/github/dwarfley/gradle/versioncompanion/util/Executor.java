package io.github.dwarfley.gradle.versioncompanion.util;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;

public final class Executor {
	
	public static List<String> execLines(String[] pCommand, String... pErrorPatterns){
		return execLines(Path.of(""), pCommand, pErrorPatterns);
	}
	
	public static List<String> execLines(Path pRootDir, String[] pCommand, String... pErrorPatterns){
		return getLines(exec(pRootDir, pCommand, pErrorPatterns));
	}
	
	public static String exec(String[] pCommand, String... pErrorPatterns){
		return exec(Path.of(""), pCommand, pErrorPatterns);
	}
	
	public static String exec(Path pRootDir, String[] pCommand, String... pErrorPatterns){
		
		try{
			
			Runtime lRuntime = Runtime.getRuntime();
			Process lProcess = lRuntime.exec(pCommand, new String[0], PathUtils.getFullPath(pRootDir).toFile());
			lProcess = lProcess.onExit().get();
			
			if(lProcess.exitValue() != 0){
				return null;
			}
			
			String lErrorText = getTextFromStream(lProcess.getErrorStream());
			
			if(lErrorText.length() > 0){
				return null;
			}
			
			String lOutputText = getTextFromStream(lProcess.getInputStream());
			
			for(String lPattern : pErrorPatterns){
				
				Matcher lErrorMatcher = Pattern.compile(lPattern).matcher(lErrorText);
				Matcher lOutputMatcher = Pattern.compile(lPattern).matcher(lOutputText);
				
				if(lErrorMatcher.find() || lOutputMatcher.find()){
					return null;
				}
				
			}
			
			return lOutputText;
			
		}catch(Exception lEx){
			return null;
		}
		
	}
	
	private static String getTextFromStream(InputStream pStream){
		
		BufferedReader lReader = new BufferedReader(new InputStreamReader(pStream));
		String lResult = lReader.lines().collect(Collectors.joining("\n"));
		
		return lResult.trim();
	}
	
	private static List<String> getLines(String pText){
		
		if(pText == null){
			return null;
		}
		
		return pText.lines().collect(Collectors.toList());
	}
	
	private Executor(){}
	
}
