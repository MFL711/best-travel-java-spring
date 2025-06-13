package com.PortaMauricio.best_travel.infraestructure.helpers;

import com.PortaMauricio.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlockListHelper {

    public void isInBlockListCustomer (String customerId){
        if (customerId.equals("WALA771012HCRGR054")){
            throw new ForbiddenCustomerException();
        }
    }
}
