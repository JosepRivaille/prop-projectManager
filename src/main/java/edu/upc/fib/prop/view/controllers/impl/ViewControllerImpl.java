package edu.upc.fib.prop.view.controllers.impl;

import edu.upc.fib.prop.business.controllers.BusinessController;
import edu.upc.fib.prop.business.controllers.impl.BusinessControllerImpl;
import edu.upc.fib.prop.view.controllers.ViewController;

public class ViewControllerImpl implements ViewController {

    private BusinessController businessController;

    public ViewControllerImpl() {
        System.out.println("Initializating view controller");
        businessController = new BusinessControllerImpl();
    }

}
