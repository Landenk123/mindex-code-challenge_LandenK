package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

        setDirectReports(employee);

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /**
     * Because direct reports can be nested, this is added to recursively set up the direct reports. If the reports were
     * intentionally not populated, then it perhaps a second method to return the full hierarchy separately would be preferred.
     **/
    private void setDirectReports(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        if (directReports != null) {
            for (int i = 0; i < directReports.size(); i++) {
                Employee directReport = employeeRepository.findByEmployeeId(directReports.get(i).getEmployeeId());
                directReports.set(i, directReport);
                setDirectReports(directReport);
            }
            employee.setDirectReports(directReports);
        }
    }

}
