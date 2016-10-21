package edu.upc.fib.prop.business.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.persistence.controllers.PersistenceController;
import edu.upc.fib.prop.persistence.controllers.impl.PersistenceControllerImpl;

public class BusinessControllerImpl implements BusinessController {

    private PersistenceController persistenceController;

    public BusinessControllerImpl() {
        System.out.println("Initializating business controller");
        this.persistenceController = new PersistenceControllerImpl();
    }
}
