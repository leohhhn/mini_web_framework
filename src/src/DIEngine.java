package src;

import src.annotations.*;

import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.*;

public class DIEngine {
    Map<String, Map<Class, Method>> routes = new HashMap<String, Map<Class, Method>>();
    Map<Class, Object> controllers = new HashMap<Class, Object>();
    Map<Class, Class> depContainer = new HashMap<Class, Class>(); // <interface, implClass>

    Map<Class, Object> depInstances = new HashMap<Class, Object>();


    public DIEngine() {
    }

    public void start() {
        ClassFetcher classFinder = new ClassFetcher();
        Set<Class> allClasses = classFinder.findAllClassesUsingClassLoader("src");

        try {
            DIEngine injector = new DIEngine();
            // main fetching loop
            for (Class c : allClasses) {
                if (!c.isAnnotationPresent(Controller.class)) continue;
                Object ctrlInstance = c.getDeclaredConstructor().newInstance();

                // instantiate controllers
                if (controllers.get(c) == null) {
                    controllers.put(c, ctrlInstance);
                    // inject dependencies
                    depInjection(c);
                }

                // GET/POST Path Method fetcher
                for (Method m : c.getDeclaredMethods()) {
                    Map<Class, Method> t = new HashMap<Class, Method>();
                    t.put(c, m);

                    if (m.isAnnotationPresent(GET.class) && m.isAnnotationPresent(Path.class) != false) {
                        Path currPath = m.getAnnotation(Path.class);
                        String packed = "get".concat(currPath.path());
                        routes.put(packed, t);
                    } else if (m.isAnnotationPresent(POST.class) && m.isAnnotationPresent(Path.class) != false) {
                        Path currPath = m.getAnnotation(Path.class);
                        String packed = "post".concat(currPath.path());
                        routes.put(packed, t);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void depInjection(Class c) {
        try {
            for (Field f : c.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class) && f.isAnnotationPresent(Qualifier.class)) {
                    f.setAccessible(true);

                    String qualifierValue = f.getAnnotation(Qualifier.class).value();
                    boolean verbose = f.getAnnotation(Autowired.class).verbose();

                    Class currInterface = Class.forName(f.getType().getName());
                    // implementation of @autowired field fetched from @qualifier
                    Class currIntImpl = Class.forName("src.".concat(qualifierValue));

                    depInjection(currIntImpl); // recursively fetch other deps

                    // instantiate fields
                    Object fieldInstance = currIntImpl.getDeclaredConstructor().newInstance();
                    System.out.println(fieldInstance.getClass()); // prints correct class

                    // todo following line throws illegal arg exception
                    //  "Can not set src.interfaces.Animal field src.TZoo.cat to java.lang.Class"
                    //  cannot figure it out :/
                    f.set(c, fieldInstance);

                    if (verbose) {
                        System.out.println("Initialized" + f.getType() + " " + f.getName() +
                                " in " + c.getName() + " on " +
                                LocalDateTime.now() + " with " + fieldInstance.hashCode());
                    }

                    depContainer.put(currInterface, currIntImpl); // fill in Dependency Container
                }
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



