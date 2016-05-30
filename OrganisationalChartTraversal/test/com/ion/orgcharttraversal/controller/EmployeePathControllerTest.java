package com.ion.orgcharttraversal.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ion.orgcharttraversal.input.InputParser;

public class EmployeePathControllerTest {

	private final String inputFile = "sampleFile.txt";
	private InputParser inputParser;
	private EmployeePathController controller;
	
	@Before
	public void setUp() {
		inputParser = new InputParser(inputFile);
		controller  = new EmployeePathController();
	}
	
	@Test
	public void test_findPath() {
		boolean success = inputParser.parseData();
		Assert.assertTrue(success);
		String[][] testData = dataForEmployeePathTest();
		for(int i=0; i< testData.length; i++) {
			Assert.assertEquals(testData[i][2], controller.findPath(testData[i][0], testData[i][1], inputParser.getNameToEmployeeMap()));
		}
	}
	
	private String[][] dataForEmployeePathTest() {
	    return new String[][] {
	        { "batman", "super ted","Batman (16) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)\n" },
	        { "test", "batman","Either one or both employees doesn't exists in records.." },
	        {"batman","   super  TED ","Batman (16) -> Black Widow (6) -> Gonzo the Great (2) -> Dangermouse (1) <- Invisible Woman (3) <- Super Ted (15)\n"},
	        {"batman", "   BATMAn ","Both employee names corresponds to same employee.."}
	    };
	}
}
