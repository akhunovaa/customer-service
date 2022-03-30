package ru.sbt.azatakhunov.customerservice.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Aggregate {

    private final List<Event> eventList;
    private final Map<String, Method> methodMap = new HashMap<>();

    protected Aggregate() {
        eventList = new ArrayList<>();
    }

    protected Aggregate(List<Event> anEventStream) {
        this();

        for (Event event : anEventStream) {
            this.routeOn(event);
        }
    }

    public abstract String getId();

    public List<Event> getEventList() {
        return eventList;
    }

    public void clearEvents() {
        this.eventList.clear();
    }

    protected void apply(Event event) {
        eventList.add(event);
        routeOn(event);
    }

    protected void routeOn(Event event) {
        Class<? extends Aggregate> rootType = this.getClass();
        Class<? extends Event> eventType = event.getClass();

        String key = rootType.getName() + ":" + eventType.getName();
        Method method = methodMap.get(key);

        if (method == null) {
            method = this.addMethod(key, rootType, eventType);
            if (method == null) {
                throw new IllegalArgumentException("nn");
            }
        }
        try {
            method.invoke(this, event);
        } catch (Exception e) {
            System.out.println("error in invoke route method : " + e.getMessage());
            e.printStackTrace();
        }


    }

    private Method addMethod(
            String aKey,
            Class<? extends Aggregate> aggregateType,
            Class<? extends Event> eventType) {
        try {
            Method method = this.getMethod(aggregateType, eventType);
            if (method == null) return null;
            method.setAccessible(true);
            methodMap.put(aKey, method);

            return method;

        } catch (Exception e) {
            throw new IllegalArgumentException("method or aggregate type err : " + e.getMessage());
        }
    }

    private Method getMethod(
            Class<? extends Aggregate> aggregateType,
            Class<? extends Event> eventType) {

        Method[] methods = aggregateType.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(OnEvent.class) == null) continue;
            for (Class<?> paramType : method.getParameterTypes()) {
                if (paramType == eventType) {
                    return method;
                }
            }
        }
        return null;
    }
}
