package com.webclient2.model.workflow;

import com.webclient2.model.employee.Datum;
import com.webclient2.model.employee.Employee;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeWorkflow {

    private Employee employees;

    public EmployeeWorkflow(Employee employees) {
        this.employees = employees;
    }

    @Description("get employee salary above 60K per year")
    public List<Datum> getSalaryOver60K() {
        return employees.getData().stream()
                .filter(employee -> employee.getEmployeeSalary() > 600000)
                .collect(Collectors.toList());
    }

    @Description("get employee age less than twenty")
    public List<Datum> getAgeLessThanTwenty() {
        return employees.getData().stream()
                .filter(employee -> employee.getEmployeeAge() < 20)
                .collect(Collectors.toList());
    }

    @Description("get total employees")
    public Long getTotalEmployees() {
        return employees.getData().stream().count();
    }
}
