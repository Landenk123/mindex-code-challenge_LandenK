package com.mindex.challenge.service.impl;

import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Service class to create a reporting structure for an employee, since values are computed only, just a read is needed
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reporting structure with id [{}]", id);

        //First, get the employee using the employee service
        Employee employee = employeeService.read(id);
        if (employee == null) {
            throw new RuntimeException("Employee ID not found: " + id);
        }

        //Using the employee, build a reporting structure
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.calculateNumberOfReports();

        return reportingStructure;
    }
}
