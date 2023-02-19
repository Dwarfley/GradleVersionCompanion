package io.github.dwarfley.gradle.versioncompanion.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.dwarfley.gradle.versioncompanion.version.VersionParser;

final class VersionParserTest {
	
	@Test
	void parseTest(){
		
		testParse(null, true);
		testParse("", true);
		
		testParse("1.2.3-!", true);
		testParse("1.2.3.4", true);
		testParse("1.x.3", true);
		testParse("1-a", true);
		testParse("1.2-a", true);
		testParse("1.2+d", true);
		
		testParse("1.2.x", false);
		testParse("1.2.3-a.b.c+d.e.f", false);
		
	}
	
	@Test
	void parseListsTest(){
		
		testParseLists("1", List.of(List.of("1"), List.of(), List.of()));
		testParseLists("1-a", List.of(List.of("1"), List.of("a"), List.of()));
		testParseLists("1+d", List.of(List.of("1"), List.of(), List.of("d")));
		testParseLists("1-a+d", List.of(List.of("1"), List.of("a"), List.of("d")));
		
	}
	
	@Test
	void parsePartsTest(){
		
		testParseParts(null, null);
		testParseParts("", null);
		
		testParseParts("1", List.of("1", "", ""));
		testParseParts("1-2", List.of("1", "2", ""));
		testParseParts("1-2+3", List.of("1", "2", "3"));
		testParseParts("1+3", List.of("1", "", "3"));
		
		testParseParts("1+2+3", null);
		testParseParts("1-2-3", List.of("1", "2-3", ""));
		
		testParseParts("1-", null);
		testParseParts("1+", null);
		testParseParts("1-+", null);
		testParseParts("1+-", List.of("1", "", "-"));
		testParseParts("1-2+", null);
		testParseParts("1-+3", null);
		
		testParseParts("-2", null);
		testParseParts("+3", null);
		testParseParts("-2+3", null);
		
	}
	
	@Test
	void parseIntListTest(){
		
		testParseIntList(null, null);
		testParseIntList(List.of(), List.of());
		
		testParseIntList(List.of("1"), List.of(1));
		testParseIntList(List.of("a"), null);
		
		testParseIntList(List.of("1", "a"), null);
		testParseIntList(List.of("a", "1"), null);
		
	}
	
	@Test
	void parseListTest(){
		
		testParseList(null, null);
		testParseList("", List.of());
		
		testParseList("1", List.of("1"));
		testParseList("1.1", List.of("1", "1"));
		
		testParseList("1.", null);
		testParseList(".1", null);
		testParseList(".", null);
		testParseList("1..1", null);
		
		testParseList("0", List.of("0"));
		testParseList("00", null);
		testParseList("01", null);
		testParseList("10", List.of("10"));
		
	}
	
	@Test
	void countCharTest(){
		
		testCountChar(null, '.', 0);
		testCountChar("", '.', 0);
		testCountChar("1", '.', 0);
		
		testCountChar(".1", '.', 1);
		testCountChar("1.", '.', 1);
		testCountChar("1.1", '.', 1);
		
		testCountChar("1.1.1", '.', 2);
		testCountChar(".1.", '.', 2);
		testCountChar("..", '.', 2);
		
	}
	
	private void testParse(String pVersion, boolean pExpected){
		assertEquals(pExpected, VersionParser.parse(pVersion) == null);
	}
	
	private void testParseLists(String pVersion, List<List<String>> pExpected){
		assertEquals(pExpected, VersionParser.parseLists(pVersion));
	}
	
	private void testParseParts(String pVersion, List<String> pExpected){
		assertEquals(pExpected, VersionParser.parseParts(pVersion));
	}
	
	private void testParseIntList(List<String> pList, List<Integer> pExpected){
		assertEquals(pExpected, VersionParser.parseIntList(pList));
	}
	
	private void testParseList(String pList, List<String> pExpected){
		assertEquals(pExpected, VersionParser.parseList(pList));
	}
	
	private void testCountChar(String pString, char pChar, int pExpected){
		assertEquals(pExpected, VersionParser.countChar(pString, pChar));
	}
	
}
