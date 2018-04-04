package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamPractice {
    public static void main(String[] args) {
	Employee e1 = new Employee("Neeraj", "ECE");
	Employee e2 = new Employee("Rahul", "CS");
	Employee e3 = new Employee("Aditya", "ECE");
	
	List<Employee> list = new ArrayList<StreamPractice.Employee>();
	list.add(e1);
	list.add(e2);
	list.add(e3);
	
	List<String> resultList =  list.stream()
	.filter(employee-> {return employee.getDeptName().equalsIgnoreCase("ECE");})
	.map(employee -> {
	    return employee.getName().toUpperCase();
	})
	.collect(Collectors.toList());
	
	resultList.stream()
	.forEach(name->{System.out.println(name);});
    }
    
    public static class Employee{
	private String name;
	private String  deptName;
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getDeptName() {
	    return deptName;
	}
	public void setDeptName(String deptName) {
	    this.deptName = deptName;
	}
	@Override
	public String toString() {
	    return "Employee [name=" + name + ", deptName=" + deptName + "]";
	}
	public Employee(String name, String deptName) {
	    super();
	    this.name = name;
	    this.deptName = deptName;
	}
	
    }
}
