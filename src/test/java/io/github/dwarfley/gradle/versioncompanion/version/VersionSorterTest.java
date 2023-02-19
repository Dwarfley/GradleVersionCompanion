package io.github.dwarfley.gradle.versioncompanion.version;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

import io.github.dwarfley.gradle.versioncompanion.version.*;

final class VersionSorterTest {
	
	@Test
	void sortTest(){
		
		List<Version> lExpected = new ArrayList<>();
		
		lExpected.add(VersionParser.parse("1.2"));
		lExpected.add(VersionParser.parse("1.2.3-a+f.e.d"));
		lExpected.add(VersionParser.parse("1.2.3-a"));
		lExpected.add(VersionParser.parse("1.2.3-a.b+dd.e.f"));
		lExpected.add(VersionParser.parse("1.2.3-a.b"));
		lExpected.add(VersionParser.parse("1.2.3-a.b.c+d.e.ff"));
		lExpected.add(VersionParser.parse("1.2.3-a.b.c"));
		lExpected.add(VersionParser.parse("1.2.3-a.b.cc+d.e.f"));
		lExpected.add(VersionParser.parse("1.2.3-a.b.cc"));
		lExpected.add(VersionParser.parse("1.2.3-aa.b.c+d.e"));
		lExpected.add(VersionParser.parse("1.2.3-aa.b.c"));
		lExpected.add(VersionParser.parse("1.2.3-c.b.a+d"));
		lExpected.add(VersionParser.parse("1.2.3-c.b.a"));
		lExpected.add(VersionParser.parse("1.2.3+d"));
		lExpected.add(VersionParser.parse("1.2.3+d.e"));
		lExpected.add(VersionParser.parse("1.2.3+d.e.f"));
		lExpected.add(VersionParser.parse("1.2.3+d.e.ff"));
		lExpected.add(VersionParser.parse("1.2.3+dd.e.f"));
		lExpected.add(VersionParser.parse("1.2.3+f.e.d"));
		lExpected.add(VersionParser.parse("1.2.3"));
		lExpected.add(VersionParser.parse("1.2.30"));
		lExpected.add(VersionParser.parse("3.2.1"));
		lExpected.add(VersionParser.parse("10.2.3"));
		lExpected.add(VersionParser.parse("11.2"));
		
		List<Version> lActual = new ArrayList<>(lExpected);
		
		Collections.reverse(lActual);
		
		assertNotEquals(lExpected, lActual);
		
		VersionSorter.sort(lActual);
		
		assertEquals(lExpected, lActual);
		
	}
	
	@Test
	void comapreTest(){
		
		testCompare(List.of(0, 0, 0), List.of(0, 0, 0), 0);
		testCompare(List.of(1, 2, 3), List.of(1, 2, 3), 0);
		testCompare(List.of(3, 2, 1), List.of(1, 2, 3), 1);
		testCompare(List.of(1, 2, 3), List.of(3, 2, 1), -1);
		
		testCompare(List.of(0, 0), List.of(0, 0), 0);
		testCompare(List.of(1, 2), List.of(1, 2), 0);
		testCompare(List.of(1, 2), List.of(2, 1), -1);
		testCompare(List.of(2, 1), List.of(1, 2), 1);
		
		testCompare(List.of(0, 0), List.of(0, 0, 0), -1);
		testCompare(List.of(0, 0, 0), List.of(0, 0), 1);
		testCompare(List.of(1, 2), List.of(1, 2, 3), -1);
		testCompare(List.of(1, 2, 3), List.of(1, 2), 1);
		testCompare(List.of(1, 2), List.of(3, 2, 1), -1);
		testCompare(List.of(1, 2, 3), List.of(2, 1), -1);
		testCompare(List.of(3, 2, 1), List.of(1, 2), 1);
		testCompare(List.of(2, 1), List.of(1, 2, 3), 1);
		
	}
	
	@Test
	void compareMajorMinorTest(){
		
		testCompareMajorMinor(0, 0, 0, 0, 0);
		testCompareMajorMinor(1, 2, 1, 2, 0);
		
		testCompareMajorMinor(2, 1, 1, 2, 1);
		testCompareMajorMinor(1, 2, 2, 1, -1);
		
		testCompareMajorMinor(1, 10, 10, 1, -1);
		testCompareMajorMinor(10, 1, 1, 10, 1);
		
		testCompareMajorMinor(2, 10, 10, 2, -1);
		testCompareMajorMinor(10, 2, 2, 10, 1);
		
	}
	
	@Test
	void compareInfoTest(){
		
		testCompareInfo(List.of(), List.of(), List.of(), List.of(), 0);
		testCompareInfo(List.of("a"), List.of(), List.of("a"), List.of(), 0);
		testCompareInfo(List.of("a"), List.of("b"), List.of("a"), List.of("b"), 0);
		
		testCompareInfo(List.of("b"), List.of("a"), List.of("c"), List.of("a"), -1);
		testCompareInfo(List.of("c"), List.of("a"), List.of("b"), List.of("a"), 1);
		
		testCompareInfo(List.of("a"), List.of("b"), List.of("a"), List.of("c"), -1);
		testCompareInfo(List.of("a"), List.of("c"), List.of("a"), List.of("b"), 1);
		
		testCompareInfo(List.of("b"), List.of("d"), List.of("c"), List.of("a"), -1);
		testCompareInfo(List.of("b"), List.of("a"), List.of("c"), List.of("d"), -1);
		testCompareInfo(List.of("c"), List.of("d"), List.of("b"), List.of("a"), 1);
		testCompareInfo(List.of("c"), List.of("a"), List.of("b"), List.of("d"), 1);
		
	}
	
	@Test
	void compareListTest(){
		
		testCompareList(List.of(), List.of(), 0);
		testCompareList(List.of("a"), List.of("a"), 0);
		
		testCompareList(List.of("a"), List.of(), -1);
		testCompareList(List.of(), List.of("a"), 1);
		
		testCompareList(List.of("a", "b"), List.of("a"), 1);
		testCompareList(List.of("a"), List.of("a", "b"), -1);
		
	}
	
	@Test
	void compareElementTest(){
		
		testCompareElement("1", "1", 0);
		testCompareElement("1", "2", -1);
		testCompareElement("2", "1", 1);
		testCompareElement("10", "1", 1);
		testCompareElement("1", "10", -1);
		testCompareElement("10", "2", 1);
		testCompareElement("2", "10", -1);
		
		testCompareElement("1", "a", -1);
		testCompareElement("a", "1", 1);
		testCompareElement("12", "a", -1);
		testCompareElement("a", "12", 1);
		testCompareElement("1", "ab", -1);
		testCompareElement("ab", "1", 1);
		
		testCompareElement("ab", "ab", 0);
		testCompareElement("a", "ab", -1);
		testCompareElement("ab", "a", 1);
		testCompareElement("abc", "ab", 1);
		testCompareElement("ab", "abc", -1);
		testCompareElement("aa", "ab", -1);
		testCompareElement("ab", "aa", 1);
		testCompareElement("aaa", "ab", -1);
		testCompareElement("ab", "aaa", 1);
		testCompareElement("c", "ab", 1);
		testCompareElement("ab", "c", -1);
		testCompareElement("cc", "ab", 1);
		testCompareElement("ab", "cc", -1);
		
	}
	
	private void testCompare(List<Integer> pA, List<Integer> pB, int pExpected){
		
		Version lVerA;
		
		if(pA.size() == 2){
			lVerA = new ShortVersion(pA.get(0), pA.get(1));
		}else{
			lVerA = new FullVersion(pA.get(0), pA.get(1), pA.get(2));
		}
		
		Version lVerB;
		
		if(pB.size() == 2){
			lVerB = new ShortVersion(pB.get(0), pB.get(1));
		}else{
			lVerB = new FullVersion(pB.get(0), pB.get(1), pB.get(2));
		}
		
		int lActual = VersionSorter.compare(lVerA, lVerB);
		lActual = getNormalized(lActual);
		
		assertEquals(pExpected, lActual);
		
	}
	
	private void testCompareMajorMinor(int pMajorA, int pMinorA, int pMajorB, int pMinorB, int pExpected){
		
		int lActual = VersionSorter.compareMajorMinor(pMajorA, pMinorA, pMajorB, pMinorB);
		lActual = getNormalized(lActual);
		
		assertEquals(pExpected, lActual);
		
	}
	
	private void testCompareInfo(List<String> pRA, List<String> pBA, List<String> pRB, List<String> pBB, int pExpected){
		
		Version lVerA = new FullVersion(0, 0, 0);
		lVerA.getReleaseInfo().addAll(pRA);
		lVerA.getBuildInfo().addAll(pBA);
		
		Version lVerB = new FullVersion(0, 0, 0);
		lVerB.getReleaseInfo().addAll(pRB);
		lVerB.getBuildInfo().addAll(pBB);
		
		int lActual = VersionSorter.compareInfo(lVerA, lVerB);
		lActual = getNormalized(lActual);
		
		assertEquals(pExpected, lActual);
		
	}
	
	private void testCompareList(List<String> pA, List<String> pB, int pExpected){
		
		int lActual = VersionSorter.compareList(pA, pB);
		lActual = getNormalized(lActual);
		
		assertEquals(pExpected, lActual);
		
	}
	
	private void testCompareElement(String pA, String pB, int pExpected){
		
		int lActual = VersionSorter.compareElement(pA, pB);
		lActual = getNormalized(lActual);
		
		assertEquals(pExpected, lActual);
		
	}
	
	private int getNormalized(int pInteger){
		
		if(pInteger == 0){
			return 0;
		}
		
		return pInteger / Math.abs(pInteger);
	}
	
}
