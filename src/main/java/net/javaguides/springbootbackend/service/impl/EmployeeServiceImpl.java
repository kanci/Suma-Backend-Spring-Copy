package net.javaguides.springbootbackend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import net.javaguides.springbootbackend.exception.ResourceNotFoundException;
import net.javaguides.springbootbackend.model.Employee;
import net.javaguides.springbootbackend.repository.EmployeeRepository;
import net.javaguides.springbootbackend.service.EmployeeService;

import lombok.Data;

@Service
public class EmployeeServiceImpl implements EmployeeService  {
	
	public EmployeeRepository employeeRepository;//usually it supposed to be private
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		//Optional<Employee> employee = employeeRepository.findById(id);
		//if(employee.isPresent()) {
		//	return employee.get();
		//}
		//else {
		//	throw new ResourceNotFoundException("Employee", "Id", id);
		//}
		
		//another approach is Lambada expression
		return employeeRepository.findById(id).orElseThrow(() -> 
				new ResourceNotFoundException("Employee", "Id", id));
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		//check whether employee with given id exists in database or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Employee", "Id", id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		
		//save existing employee to DB
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	}

	@Override
	public void deleteEmployee(long id) {
		
		//check whether employee exist in a DB or not with ID
		employeeRepository.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("Employee", "Id", id));
		
		employeeRepository.deleteById(id);
		
	}
	

	
	
}
