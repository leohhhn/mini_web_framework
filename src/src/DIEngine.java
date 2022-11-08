package src;

import java.lang.reflect.*;
import java.util.*;

public class DIEngine {
    Map<String, Map<Class, Method>> routes = new HashMap<String, Map<Class, Method>>();
    Map<Class, Object> controllers = new HashMap<Class, Object>();

    public DIEngine() {
    }

    public void start() {
        AccessingAllClassesInPackage classFinder = new AccessingAllClassesInPackage();
        Set<Class> allClasses = classFinder.findAllClassesUsingClassLoader("src");

        try {
            DIEngine injector = new DIEngine();
            // main fetching loop
            for (Class c : allClasses) {
                if (!c.isAnnotationPresent(Controller.class)) continue;
                Object ctrlInstance = c.getDeclaredConstructor().newInstance();

                if (controllers.get(c) == null) // put instance in mapping if not instantiated already
                    controllers.put(c, ctrlInstance);

                // Method fetcher
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

                ArrayList<Field> awFields = new ArrayList<Field>();
                for (Field f : c.getDeclaredFields()) {
                    if (f.isAnnotationPresent(Autowired.class)) {
                        awFields.add(f);


                    }

                }§§
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



