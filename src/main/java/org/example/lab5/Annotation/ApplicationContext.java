package org.example.lab5.Annotation;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    private final Map<Class<?>, Object> container = new HashMap<>();

    public ApplicationContext(String basePackage) throws Exception {
        scanAndLoadComponents(basePackage);
    }

    private void scanAndLoadComponents(String basePackage) throws Exception {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> beans = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> bean : beans) {
            registerBean(bean);
        }
    }

    private void registerBean(Class<?> bean) throws Exception {
        Object instance = bean.getDeclaredConstructor().newInstance();
        container.put(bean, instance);
    }

    public <T> T getBean(Class<T> clazz) throws Exception {
        Object instance = container.get(clazz);
        if (instance == null) {
            registerBean(clazz);
        }
        instance = container.get(clazz);
        injectDependencies(instance);
        return clazz.cast(instance);
    }

    private void injectDependencies(Object bean) throws Exception {
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                Object dependency = container.get(field.getType());
                field.set(bean, dependency);
            }
        }
    }
}
