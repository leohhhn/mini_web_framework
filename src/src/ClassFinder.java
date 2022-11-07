package src;

import javax.management.MBeanNotificationInfo;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.*;

public class ClassFinder {

    public ClassFinder() {
    }

    public void start() {
        AccessingAllClassesInPackage classFinder = new AccessingAllClassesInPackage();
        Set<Class> allClasses = classFinder.findAllClassesUsingClassLoader("src");
        ArrayList<Class> controllers = new ArrayList<Class>();

        Map<String, Map<Class, Method>> routes = new HashMap<String, Map<Class, Method>>();

        // main fetching loop
        for (Class c : allClasses) {
            if (!c.isAnnotationPresent(Controller.class)) continue;
            controllers.add(c);

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
    }


}



