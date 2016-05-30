package com.ion.orgcharttraversal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.ion.orgcharttraversal.dataobjects.Employee;

public class EmployeePathController {

	public String findPath(String emp1Name, String emp2Name, Map<String,List<Employee>> nameToEmployeeMap) {
		emp1Name = getFormattedEmployeeName(emp1Name);
		emp2Name = getFormattedEmployeeName(emp2Name);
		List<Employee> emp1NameList = nameToEmployeeMap.get(emp1Name);
		List<Employee> emp2NameList = nameToEmployeeMap.get(emp2Name);
		
		if(null == emp1NameList || emp2NameList == null) {
			return "Either one or both employees doesn't exists in records..";
		}
		
		return getPathBetweenEmployees(emp1Name, emp1NameList, emp2Name, emp2NameList);
	}
	
	private String getPathBetweenEmployees(String emp1Name, List<Employee> emp1NameList, String emp2Name, List<Employee> emp2NameList) {
		StringBuilder output = new StringBuilder("");
		if((emp1Name.equalsIgnoreCase(emp2Name))) {
			if(emp1NameList.size() <= 1) {
				output.append("Both employee names corresponds to same employee..");
			} else {
				for(int i=0; i< emp1NameList.size() -1; i++) {
					for(int j=i+1; j< emp1NameList.size(); j++) { 
						output.append(getPath(emp1NameList.get(i), emp1NameList.get(j)));
					}
				}
			}
		} else {
			for (Employee one : emp1NameList) {
				for (Employee two : emp2NameList) {
					output.append(getPath(one, two));
				}
			}
		}
		return output.toString();
	}

	private String getPath(Employee one, Employee two) {
		int hierarchyLevelDiff = one.getHierarchyLevel() - two.getHierarchyLevel();
		List<Employee> emp1HierarchyList = new ArrayList<>();
		List<Employee> emp2HierarchyList = new ArrayList<>();
		if(hierarchyLevelDiff > 0) {
			for(int i=0; i< hierarchyLevelDiff; i++) {
				emp1HierarchyList.add(one);
				one = one.getManager();
			}
		} else if(hierarchyLevelDiff < 0) {
			for(int i=0; i< Math.abs(hierarchyLevelDiff); i++) {
				emp2HierarchyList.add(two);
				two = two.getManager();
			}
		}
		return getOutputPathString(one,emp1HierarchyList, two, emp2HierarchyList);
	}
	
	private String getOutputPathString(Employee one, List<Employee> emp1HierarchyList, Employee two, List<Employee> emp2HierarchyList) {
		Employee commonManager = null, manager1 = one.getManager(), manager2 = two.getManager();
		if(one == two) {
			commonManager = one;
		} else {
			emp1HierarchyList.add(one);
			emp2HierarchyList.add(two);
			for (; manager1 != manager2 ;) {
				emp1HierarchyList.add(manager1);
				emp2HierarchyList.add(manager2);
				manager1 = manager1.getManager();
				manager2 = manager2.getManager();
			}
			commonManager = manager1;
		}
		return createOutputString(emp1HierarchyList, emp2HierarchyList, commonManager);
	}
	
	private String createOutputString(List<Employee> oneList,
			List<Employee> twoList, Employee commonManager) {
		StringBuilder sb = new StringBuilder("");
		for(Employee emp: oneList) {
			sb.append(emp+" -> ");
		}
		sb.append(commonManager);
		ListIterator<Employee> iterator = twoList.listIterator(twoList.size());
		for(;iterator.hasPrevious();) {
			sb.append(" <- "+iterator.previous());
		}
		sb.append("\n");
		return sb.toString();
	}
	
	private static String getFormattedEmployeeName(String args) {
		StringBuilder sb = new StringBuilder("");
		for(String s : args.split(" ")) {
			if(!s.isEmpty()) {
				sb.append(s+" ");
			}
		}
		return sb.toString().trim().toLowerCase();
	}
}
