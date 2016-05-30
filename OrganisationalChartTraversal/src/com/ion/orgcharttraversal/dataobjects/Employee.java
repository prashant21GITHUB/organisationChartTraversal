package com.ion.orgcharttraversal.dataobjects;

import java.util.HashSet;
import java.util.Set;

public class Employee {

	private final String employeeName;
	private final int employeeId;
	private Employee manager;
	private final Set<Employee> subEmployees;
	private int hierarchyLevel;
	
	public Employee(int id, String name) {
		this.employeeId = id;
		this.employeeName = name;
		this.subEmployees = new HashSet<>();
	}
	
	public Set<Employee> getSubEmployees() {
		return subEmployees;
	}
	
	public int getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
	}
	
	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}
	
	@Override
	public int hashCode() {
		final int h = 31;
		int result = 1;
		result = h * result + employeeId;
		result = h * result + ((employeeName == null) ? 0 : employeeName.toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeId != other.employeeId)
			return false;
		if(!this.employeeName.equalsIgnoreCase(other.employeeName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.employeeName +" (" +this.employeeId +")";
	}
}
