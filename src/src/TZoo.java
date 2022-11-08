package src;

import src.annotations.Autowired;
import src.annotations.Qualifier;
import src.interfaces.Animal;
import src.interfaces.Establishment;

class TZoo implements Establishment {

    @Autowired(verbose = false)
    @Qualifier(value = "TCat")
    Animal cat;

    public TZoo() {
    }

    @Override
    public void establish() {
        System.out.println("This Zoo is Established, and look below! A cat!");
        System.out.println(cat.toString());
    }
}
