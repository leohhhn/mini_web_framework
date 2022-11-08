package src;

import src.interfaces.Animal;

public class TCat implements Animal {

    public TCat() {

    }

    @Override
    public void makeSound() {
        System.out.println("MEOW");
    }


}
