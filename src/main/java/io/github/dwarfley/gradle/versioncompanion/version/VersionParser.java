package io.github.dwarfley.gradle.versioncompanion.version;

import java.util.*;
import java.util.stream.Collectors;

public final class VersionParser {
	
	public static Version parse(String pVersion){
		
		if(pVersion == null || !pVersion.matches("[a-zA-Z0-9.+-]*")){
			return null;
		}
		
		List<List<String>> lLists = parseLists(pVersion);
		
		if(lLists == null){
			return null;
		}
		
		List<String> lFirstList = lLists.get(0);
		List<String> lSecondList = lLists.get(1);
		List<String> lThirdList = lLists.get(2);
		
		if(lFirstList.size() == 3 && lFirstList.get(2).toLowerCase().equals("x")){
			lFirstList.remove(2);
		}
		
		List<Integer> lFirstIntList = parseIntList(lFirstList);
		
		if(lFirstIntList == null || lFirstIntList.size() < 2 || lFirstIntList.size() > 3){
			return null;
		}
		
		Version lVersion;
		
		if(lFirstIntList.size() == 2){
			lVersion = new ShortVersion(lFirstIntList.get(0), lFirstIntList.get(1));
		}else{
			lVersion = new FullVersion(lFirstIntList.get(0), lFirstIntList.get(1), lFirstIntList.get(2));
		}
		
		lVersion.getReleaseInfo().addAll(lSecondList);
		lVersion.getBuildInfo().addAll(lThirdList);
		
		if(lVersion instanceof ShortVersion){
			if(lVersion.getReleaseInfo().size() != 0 || lVersion.getBuildInfo().size() != 0){
				return null;
			}
		}
		
		return lVersion;
	}
	
	static List<List<String>> parseLists(String pVersion){
		
		List<String> lParts = parseParts(pVersion);
		
		if(lParts == null){
			return null;
		}
		
		List<List<String>> lResult = new ArrayList<>();
		
		for(String lPart : lParts){
			
			List<String> lPartList = parseList(lPart);
			
			if(lPartList == null){
				return null;
			}
			
			lResult.add(lPartList);
			
		}
		
		return lResult;
	}
	
	static List<String> parseParts(String pVersion){
		
		if(pVersion == null || pVersion.length() < 1){
			return null;
		}
		
		if(countChar(pVersion, '+') > 1){
			return null;
		}
		
		List<String> lResult = new ArrayList<>();
		String lTemp = pVersion;
		
		for(int i = 0; i < 3; i++){
			
			int lStart = 0;
			int lEnd = lTemp.length();
			
			String lDelimiter = null;
			
			if(i < 2){
				lDelimiter = i == 0 ? "+" : "-";
			}
			
			if(lDelimiter != null){
				if(lTemp.contains(lDelimiter)){
					lStart = lTemp.indexOf(lDelimiter);
				}else{
					lResult.add(0, "");
					continue;
				}
			}
			
			int lOffset = lDelimiter != null ? lDelimiter.length() : 0;
			String lPart = lTemp.substring(lStart + lOffset, lEnd);
			
			if(lPart.length() < 1){
				return null;
			}
			
			lResult.add(0, lPart);
			lTemp = lTemp.substring(0, lStart);
			
		}
		
		return lResult;
	}
	
	static List<Integer> parseIntList(List<String> pList){
		
		if(pList == null){
			return null;
		}
		
		List<Integer> lResult = new ArrayList<>();
		
		for(String lElement : pList){
			
			try{
				lResult.add(Integer.parseInt(lElement));
			}catch(Exception lEx){
				return null;
			}
			
		}
		
		return lResult;
	}
	
	static List<String> parseList(String pList){
		
		if(pList == null){
			return null;
		}
		
		if(pList.length() < 1){
			return new ArrayList<>();
		}
		
		if(pList.startsWith(".") || pList.contains("..") || pList.endsWith(".")){
			return null;
		}
		
		List<String> lResult = Arrays.stream(pList.split("\\.")).collect(Collectors.toList());
		
		for(String lElement : lResult){
			if(lElement.matches("0[0-9]+")){
				return null;
			}
		}
		
		return lResult;
	}
	
	static int countChar(String pString, char pChar){
		
		if(pString == null){
			return 0;
		}
		
		return (int) pString.chars().filter(pC -> pC == pChar).count();
	}
	
	private VersionParser(){}
	
}
