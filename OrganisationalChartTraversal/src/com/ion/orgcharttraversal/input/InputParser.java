package com.ion.orgcharttraversal.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;

import com.ion.orgcharttraversal.dataobjects.Employee;

public class InputParser {

	private Employee rootEmployee;
	private final String inputFile;
	private final Map<Integer, Set<Employee>> managerToEmployeeMap; 
	private final Map<Integer, Employee> employeeIdMap;
	private final Map<String, List<Employee>> nameToEmployeeMap;
	
	public InputParser(String inputFile) {
		this.inputFile = inputFile;
		managerToEmployeeMap = new HashMap<>(); 
		employeeIdMap = new HashMap<>();
		nameToEmployeeMap = new HashMap<>();
	}
	
	public boolean parseData() {
		try (BufferedReader reader = new BufferedReader(new FileReader(
				inputFile))) {

			reader.readLine(); // Ignored as the the first record is heading in input file.
			String line;
			StringBuilder nameBuilder = new StringBuilder("");
			for(;(line = reader.readLine()) != null;) {
				if(!mapRecord(line, nameBuilder)){
					return false;
				}
				nameBuilder.delete(0, nameBuilder.length());
			}
			prepareEmployeeHierarchy();
			prepareEmployeeHierarchyLevel();
			return true;
		} catch (IOException ex) {
			System.out.println("Exception while parsing the data...  "+ex);
			return false;
		}
	}
	
	private boolean mapRecord(String line, StringBuilder nameBuilder) {
		String record[] = line.split(Pattern.quote("|"));
		if(record[1].trim().isEmpty() || record[2].trim().isEmpty() ) {
			throw new NullPointerException("Either employee ID or name is missing..");
		}
		String name[] = record[2].trim().split(" ");
		for(String s : name) {
			if(!s.isEmpty())
			nameBuilder.append(s+" ");
		}
		int id = Integer.parseInt(record[1].trim());
		if(employeeIdMap.containsKey(id)) {
			System.out.println("INVALID records, More than one records with same employeeId...");
			return false;
		}
		Integer managerId = record[3].trim().isEmpty() ? null : Integer.parseInt(record[3].trim());
		Employee emp = getRecordEmployee(id, nameBuilder.toString());
		putRecordInManagerEmpMap(managerId,emp);
		putRecordInNameEmpMap(nameBuilder.toString().trim().toLowerCase(), emp);
		employeeIdMap.put(id, emp);
		return true;
	}
	
	private void putRecordInNameEmpMap(String name, Employee emp) {
		if(nameToEmployeeMap.containsKey(name)) {
			nameToEmployeeMap.get(name).add(emp);
		} else {
			List<Employee> list = new ArrayList<>();
			list.add(emp);
			nameToEmployeeMap.put(name, list);
		}
	}

	private void putRecordInManagerEmpMap(Integer managerId, Employee emp) {
		if(null == managerId) {
			rootEmployee = emp;
		}
		if(managerToEmployeeMap.containsKey(managerId)) {
			managerToEmployeeMap.get(managerId).add(emp);
		} else {
			Set<Employee> set = new HashSet<>();
			set.add(emp);
			managerToEmployeeMap.put(managerId, set);
		}
	}
	
	private Employee getRecordEmployee(int id, String name) {
		return new Employee(id,name.trim());
	}
	
	private void prepareEmployeeHierarchy() {
		if(null != managerToEmployeeMap && null != employeeIdMap) {
			Employee manager;
			for(Map.Entry<Integer, Set<Employee>> entry: managerToEmployeeMap.entrySet()) {
				manager = employeeIdMap.get(entry.getKey());
				for (Employee emp : entry.getValue()) {
					emp.setManager(manager);
					if(null != manager) {
						manager.getSubEmployees().add(emp);
					}
				}
			}
		}
	}
	
	private void prepareEmployeeHierarchyLevel() {
		if(null != rootEmployee) {
			rootEmployee.setHierarchyLevel(1);
			Employee emp = rootEmployee, temp;
			Queue<Employee> queue = new LinkedList<>();
			queue.add(emp);
			for(;!queue.isEmpty();) {
				temp = queue.poll();
				for(Employee e : temp.getSubEmployees()) {
					e.setHierarchyLevel(temp.getHierarchyLevel() + 1);
					queue.add(e);
				}
			}
		}
	}
	
	public void printOrganisationHierarchy() {
		if(null != rootEmployee) {
			System.out.println("                HIERARCHY OF ORGANISATION:");
			Employee emp = rootEmployee, temp;
			Queue<Employee> queue = new LinkedList<>();
			queue.add(emp);
			for(;!queue.isEmpty();) {
				temp = queue.poll();
				System.out.println(temp);
				for(Employee e : temp.getSubEmployees()) {
					System.out.println("                ---->"+e);
					queue.add(e);
				}
			}
			System.out.println();
		}
	}
	
	public Employee getRootEmployee() {
		return rootEmployee;
	}
	
	public Map<String, List<Employee>> getNameToEmployeeMap() {
		return nameToEmployeeMap;
	}
}
