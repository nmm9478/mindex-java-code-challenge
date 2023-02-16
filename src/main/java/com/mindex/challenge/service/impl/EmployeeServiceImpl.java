package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        employee.fillInDirectReports(this);
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    public int getNumberOfReports(String employeeId) {
        
        // note:
        // It is assumed that there won't be someone reporting to multiple people...
        //  (referring to 'distinct reports' in README.)
        
        Employee employee = this.read(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee not found.");
        }
        
        int reportsCount = 0;

        List<Employee> directReports = employee.getDirectReports();
        if (directReports != null) {
            for (Employee employeeReporting : directReports) {
                reportsCount += 1 + getNumberOfReports(employeeReporting.getEmployeeId());
            }
        }
        
        return reportsCount;
    }
}
