package com.ion.orgcharttraversal.main;

import java.util.List;
import java.util.Map;

import com.ion.orgcharttraversal.controller.EmployeePathController;
import com.ion.orgcharttraversal.dataobjects.Employee;
import com.ion.orgcharttraversal.input.InputParser;
import com.ion.orgcharttraversal.output.OutputPrinter;

public class Main {

	public static void main(String[] args) {
		
		if(args == null || args.length <3) {
			System.out.println("Illegal number of arguments");
			return;
		}
		
		String inputFile = args[0];
		String emp1 = args[1];
		String emp2 = args[2];
		
		InputParser inputParser = new InputParser(inputFile);
		boolean successfulParse = inputParser.parseData();
		if(!successfulParse) {
			System.out.println("Data parsing is unsuccessful..");
			return;
		}
		
		//To print hierarchy of organisation.., UNComment the below line
		//inputParser.printOrganisationHierarchy();
		
		// Name to list of employee map for fast search
		Map<String, List<Employee>> nameToEmployeeMap = inputParser.getNameToEmployeeMap();
		EmployeePathController controller = new EmployeePathController();
		String path = controller.findPath(emp1, emp2, nameToEmployeeMap);
		
		OutputPrinter printer = new OutputPrinter();
		printer.printOutputOnConsole(path);
	}
}
