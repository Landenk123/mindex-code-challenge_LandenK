package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

//Following the established format of the other services
public interface CompensationService {
    Compensation create(Compensation compensation);

    Compensation read(String id);
}
