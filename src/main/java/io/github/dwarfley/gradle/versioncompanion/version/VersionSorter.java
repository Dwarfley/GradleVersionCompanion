package io.github.dwarfley.gradle.versioncompanion.version;

import java.util.*;

public final class VersionSorter {
	
	public static void sort(List<Version> pVersions){
		Collections.sort(pVersions, (pA, pB) -> {
			return compare(pA, pB);
		});
	}
	
	static int compare(Version pA, Version pB){
		
		if(pA instanceof ShortVersion lA && pB instanceof ShortVersion lB){
			return compareVersion(lA, lB);
		}else if(pA instanceof FullVersion lA && pB instanceof ShortVersion lB){
			return compareVersion(lA, lB);
		}else if(pA instanceof ShortVersion lA && pB instanceof FullVersion lB){
			return compareVersion(lA, lB);
		}else if(pA instanceof FullVersion lA && pB instanceof FullVersion lB){
			return compareVersion(lA, lB);
		}else{
			return 0;
		}
		
	}
	
	static int compareVersion(ShortVersion pA, ShortVersion pB){
		
		int lResult = compareMajorMinor(pA.getMajor(), pA.getMinor(), pB.getMajor(), pB.getMinor());
		
		if(lResult == 0){
			lResult = compareInfo(pA, pB);
		}
		
		return lResult;
	}
	
	static int compareVersion(FullVersion pA, ShortVersion pB){
		
		int lResult = compareMajorMinor(pA.getMajor(), pA.getMinor(), pB.getMajor(), pB.getMinor());
		
		if(lResult == 0){
			return 1;
		}
		
		return lResult;
	}
	
	static int compareVersion(ShortVersion pA, FullVersion pB){
		
		int lResult = compareMajorMinor(pA.getMajor(), pA.getMinor(), pB.getMajor(), pB.getMinor());
		
		if(lResult == 0){
			return -1;
		}
		
		return lResult;
	}
	
	static int compareVersion(FullVersion pA, FullVersion pB){
		
		int lResult = compareMajorMinor(pA.getMajor(), pA.getMinor(), pB.getMajor(), pB.getMinor());
		
		if(lResult == 0){
			lResult = Integer.compare(pA.getPatch(), pB.getPatch());
		}
		
		if(lResult == 0){
			lResult = compareInfo(pA, pB);
		}
		
		return lResult;
	}
	
	static int compareMajorMinor(int pMajorA, int pMinorA, int pMajorB, int pMinorB){
		
		int lResult = Integer.compare(pMajorA, pMajorB);
		
		if(lResult == 0){
			lResult = Integer.compare(pMinorA, pMinorB);
		}
		
		return lResult;
	}
	
	static int compareInfo(Version pA, Version pB){
		
		int lResult = compareList(pA.getReleaseInfo(), pB.getReleaseInfo());
		
		if(lResult == 0){
			lResult = compareList(pA.getBuildInfo(), pB.getBuildInfo());
		}
		
		return lResult;
	}
	
	static int compareList(List<String> pA, List<String> pB){
		
		if(pA.size() == 0 && pB.size() == 0){
			return 0;
		}else if(pA.size() == 0){
			return 1;
		}else if(pB.size() == 0){
			return -1;
		}
		
		int lIndex = 0;
		
		while(lIndex < pA.size() && lIndex < pB.size()){
			
			int lResult = compareElement(pA.get(lIndex), pB.get(lIndex));
			
			if(lResult != 0){
				return lResult;
			}
			
			lIndex++;
			
		}
		
		return Integer.compare(pA.size(), pB.size());
	}
	
	static int compareElement(String pA, String pB){
		
		String lIntRegex = "[0-9]+";
		
		boolean lIsIntA = pA.matches(lIntRegex);
		boolean lIsIntB = pB.matches(lIntRegex);
		
		if(lIsIntA && lIsIntB){
			
			try{
				
				Integer lA = Integer.parseInt(pA);
				Integer lB = Integer.parseInt(pB);
				
				return Integer.compare(lA, lB);
				
			}catch(Exception lEx){
				return 0;
			}
			
		}
		
		if(lIsIntA){
			return -1;
		}
		
		if(lIsIntB){
			return 1;
		}
		
		return pA.compareTo(pB);
	}
	
	private VersionSorter(){}
	
}
