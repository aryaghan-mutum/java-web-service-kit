package com.webclient2;

import com.webclient2.request.employee2.EmployeeRequest;
import com.webclient2.response.employee.EmployeeResponse;
import com.webclient2.service.employee.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.webclient2.request.employee.*;
import com.webclient2.workflow.*;

import java.util.List;

public class EmployeeTest {

	private EmployeeService employeeResponse;
	private Employee employees;
	private EmployeeWorkflow employeeWorkflow;

	public EmployeeTest() {
		employeeResponse = new EmployeeService();
		final String employeeUrl = "http://dummy.restapiexample.com/api/v1/employees";
		employees = employeeResponse.getEmployeeUserResponse(employeeUrl);
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
		final String url = "https://reqres.in/api/users";
		MultiValueMap<String, String> newEmployeeBodyMap = new LinkedMultiValueMap<>();
		newEmployeeBodyMap.add("name", "Automation Test Employee");
		newEmployeeBodyMap.add("job", "Test Job");
		String employeeResponse = new EmployeeService().postEmployee(url, newEmployeeBodyMap);
		System.out.println(employeeResponse);
	}

	@Test
	@Description("set request body and validate the change in response by calling post service")
	@DisplayName("set request body and validate the change in response by calling post service")
	void testCreateEmployeeServicePost2()  {
		// fire a post service and check a successful response
		final String url = "http://dummy.restapiexample.com/api/v1/create";
		EmployeeService employeeService = new EmployeeService();
		EmployeeResponse employeeResponseBeforeSettingData = employeeService.postEmployee2(url, employeeService.setRequestBody());
		Assertions.assertEquals("success", employeeResponseBeforeSettingData.getStatus());

		// before setting the data
		Assertions.assertEquals(employeeResponseBeforeSettingData.getData().getName(), "test");
		Assertions.assertEquals(employeeResponseBeforeSettingData.getData().getSalary(), "123");
		Assertions.assertEquals(employeeResponseBeforeSettingData.getData().getAge(), "23");

		// setting the request body to different data
		EmployeeRequest employeeRequest = new EmployeeRequest();
		employeeRequest.setName("test2");
		employeeRequest.setSalary("30,000");
		employeeRequest.setAge("30");

		// after setting the data
		EmployeeResponse employeeResponseAfterSettingData = employeeService.postEmployee2(url, employeeRequest);
		Assertions.assertEquals(employeeResponseAfterSettingData.getData().getName(), "test2");
		Assertions.assertEquals(employeeResponseAfterSettingData.getData().getSalary(), "30,000");
		Assertions.assertEquals(employeeResponseAfterSettingData.getData().getAge(), "30");
	}


}
