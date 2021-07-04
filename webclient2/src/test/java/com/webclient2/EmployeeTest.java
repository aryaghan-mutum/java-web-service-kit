package com.webclient2;

import com.webclient2.model.employee.Datum;
import com.webclient2.model.employee.Employee;
import com.webclient2.model.service.EmployeeService;
import com.webclient2.model.workflow.EmployeeWorkflow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class EmployeeTest {
	
	private final String EMPLOYEE_URL = "http://dummy.restapiexample.com/api/v1/employees";
	private EmployeeService employeeResponse;
	private EmployeeWorkflow employeeWorkflow;
	private Employee employees;

	public EmployeeTest() {
		employeeResponse = new EmployeeService();
		employees = employeeResponse.getEmployeeUserResponse(EMPLOYEE_URL);
		employeeWorkflow = new EmployeeWorkflow(employees);
	}

	@Test
	void testSuccessfulResponse() {
		final String responseMsg = employees.getMessage();
		final String responseStatus = employees.getStatus();
		Assertions.assertEquals("Successfully! All records has been fetched.", responseMsg);
		Assertions.assertEquals("success", responseStatus);
	}

	@Test
	void testTotalEmployees() {
		Long totalEmployees = employeeWorkflow.getTotalEmployees();
		Assertions.assertEquals(24, totalEmployees);
	}

	@Test
	void testSalaryAbove60() {
		List<Datum> salaryList = employeeWorkflow.getSalaryOver60K();
		Assertions.assertEquals(2, salaryList.size());
	}

	@Test
	void testAgeLessThanTwenty() {
		List<Datum> ageList = employeeWorkflow.getAgeLessThanTwenty();
		Assertions.assertEquals(1, ageList.size());
	}
	
	@Test
	void testPost() {
		MultiValueMap<String, String> newEmployeeMap = new LinkedMultiValueMap<>();
		newEmployeeMap.add("name", "Automation Test Employee");
		newEmployeeMap.add("salary", "123456");
		newEmployeeMap.add("age", "23");
		Employee employeeResponse = new EmployeeService().postEmployee(EMPLOYEE_URL, newEmployeeMap);
	}


}
