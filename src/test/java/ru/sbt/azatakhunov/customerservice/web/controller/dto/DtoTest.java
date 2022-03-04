package ru.sbt.azatakhunov.customerservice.web.controller.dto;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.smallrye.common.constraint.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * A utility class which allows for testing entity and transfer object classes. This is mainly for code coverage since
 * these types of objects are normally nothing more than getters and setters. If any logic exists in the method, then
 * the get method name should be sent in as an ignored field and a custom test function should be written.
 *
 * @param <T> The object type to test.
 */
public abstract class DtoTest<T> {

    /**
     * A map of default mappers for common objects.
     */
    private static final ImmutableMap<Class<?>, Supplier<?>> DEFAULT_MAPPERS;

    static {
        final ImmutableMap.Builder<Class<?>, Supplier<?>> mapperBuilder = ImmutableMap.builder();

        /* Primitives */
        mapperBuilder.put(int.class, () -> 0);
        mapperBuilder.put(double.class, () -> 0.0d);
        mapperBuilder.put(float.class, () -> 0.0f);
        mapperBuilder.put(long.class, () -> 0L);
        mapperBuilder.put(boolean.class, () -> true);
        mapperBuilder.put(short.class, () -> (short) 0);
        mapperBuilder.put(byte.class, () -> (byte) 0);
        mapperBuilder.put(char.class, () -> (char) 0);

        mapperBuilder.put(Integer.class, () -> 0);
        mapperBuilder.put(Double.class, () -> 0.0);
        mapperBuilder.put(Float.class, () -> 0.0f);
        mapperBuilder.put(Long.class, () -> 0L);
        mapperBuilder.put(Boolean.class, () -> Boolean.TRUE);
        mapperBuilder.put(Short.class, () -> (short) 0);
        mapperBuilder.put(Byte.class, () -> (byte) 0);
        mapperBuilder.put(Character.class, () -> (char) 0);

        mapperBuilder.put(BigDecimal.class, () -> BigDecimal.ONE);
        mapperBuilder.put(Date.class, Date::new);

        /* Collection Types. */
        mapperBuilder.put(Set.class, Collections::emptySet);
        mapperBuilder.put(SortedSet.class, Collections::emptySortedSet);
        mapperBuilder.put(List.class, Collections::emptyList);
        mapperBuilder.put(Map.class, Collections::emptyMap);
        mapperBuilder.put(SortedMap.class, Collections::emptySortedMap);

        DEFAULT_MAPPERS = mapperBuilder.build();
    }

    /**
     * The get fields to ignore and not try to test.
     */
    private final Set<String> ignoredGetFields;

    /**
     * A custom mapper. Normally used when the test class has abstract objects.
     */
    private final ImmutableMap<Class<?>, Supplier<?>> mappers;

    private final boolean useSuper;

    /**
     * Creates an instance of {@link DtoTest} with the default ignore fields.
     */
    protected DtoTest() {
        this(null, null, false);
    }

    /**
     * Creates an instance of {@link DtoTest} with ignore fields and additional custom mappers.
     *
     * @param customMappers Any custom mappers for a given class type.
     * @param ignoreFields  The getters which should be ignored (e.g., "getId" or "isActive").
     * @param useSuper      Whether fields of parent class should be tested too
     */
    protected DtoTest(@Nullable Map<Class<?>, Supplier<?>> customMappers, @Nullable Set<String> ignoreFields, boolean useSuper) {
        this.ignoredGetFields = new HashSet<>();
        if (ignoreFields != null) {
            this.ignoredGetFields.addAll(ignoreFields);
        }
        this.ignoredGetFields.add("getClass");
        this.ignoredGetFields.add("getFixVersionsList");

        if (customMappers == null) {
            this.mappers = DEFAULT_MAPPERS;
        } else {
            final ImmutableMap.Builder<Class<?>, Supplier<?>> builder = ImmutableMap.builder();
            builder.putAll(customMappers);
            builder.putAll(DEFAULT_MAPPERS);
            this.mappers = builder.build();
        }

        this.useSuper = useSuper;
    }

    public static List<Field> getFieldsUpTo(Class<?> clazz) {

        List<Field> currentClassFields = Lists.newArrayList(clazz.getDeclaredFields());
        Class<?> parentClass = clazz.getSuperclass();

        if (parentClass != null) {
            List<Field> parentClassFields = getFieldsUpTo(parentClass);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }

    /**
     * Calls a getter and verifies the result is what is expected.
     *
     * @param fieldName The field name (used for error messages).
     * @param getter    The get {@link Method}.
     * @param instance  The test instance.
     * @param expected  The expected result.
     * @throws IllegalAccessException    if this Method object is enforcing Java language access control and the underlying
     *                                   method is inaccessible.
     * @throws IllegalArgumentException  if the method is an instance method and the specified object argument is not an
     *                                   instance of the class or interface declaring the underlying method (or of a subclass or implementor
     *                                   thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for
     *                                   primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to
     *                                   the corresponding formal parameter type by a method invocation conversion.
     * @throws InvocationTargetException if the underlying method throws an exception.
     */
    private void callGetter(String fieldName, Method getter, T instance, Object expected)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        final Object getResult = getter.invoke(instance);

        if (getter.getReturnType().isPrimitive()) {
            /* Calling assetEquals() here due to autoboxing of primitive to object type. */
            Assertions.assertEquals(expected, getResult, fieldName + " is different");
        } else {
            /* This is a normal object. The object passed in should be the exactly same object we get back. */
            Assertions.assertSame(expected, getResult, fieldName + " is different");
        }
    }

    /**
     * Creates an object for the given {@link Class}.
     *
     * @param fieldName The name of the field.
     * @param clazz     The {@link Class} type to create.
     * @return A new instance for the given {@link Class}.
     */
    private Object createObject(String fieldName, Class<?> clazz) {

        try {
            final Supplier<?> supplier = this.mappers.get(clazz);
            if (supplier != null) {
                return supplier.get();
            }

            if (clazz.isEnum()) {
                return clazz.getEnumConstants()[0];
            }

            return clazz.getConstructor(new Class<?>[]{}).newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Unable to create objects for field '" + fieldName + "'.", e);
        }
    }

    /**
     * Returns an instance to use to test the get and set methods.
     *
     * @return An instance to use to test the get and set methods.
     */
    protected abstract T getInstance();

    /**
     * Tests all the getters and setters. Verifies that when a set method is called, that the get method returns the
     * same thing. This will also use reflection to set the field if no setter exists (mainly used for user immutable
     * entities but Hibernate normally populates).
     *
     * @throws Exception If an expected error occurs.
     */
    @Test
    public void testGettersAndSetters() throws Exception {
        /* Sort items for consistent test runs. */
        final SortedMap<String, GetterSetterPair> getterSetterMapping = new TreeMap<>();

        final T instance = getInstance();

        List<Field> fields;
        if (useSuper) {
            fields = getFieldsUpTo(instance.getClass());
        } else {
            fields = Arrays.asList(instance.getClass().getDeclaredFields());
        }

        for (final Method method : instance.getClass().getMethods()) {
            final String methodName = method.getName();
            if (this.ignoredGetFields.contains(methodName)) {
                continue;
            }

            String objectName;
            if (methodName.startsWith("get") && method.getParameters().length == 0) {
                /* Found the get method. */
                objectName = methodName.substring("get".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            } else if (methodName.startsWith("set") && method.getParameters().length == 1) {
                /* Found the set method. */
                objectName = methodName.substring("set".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setSetter(method);
            } else if (methodName.startsWith("is") && method.getParameters().length == 0) {
                /* Found the is method, which really is a get method. */
                objectName = methodName.substring("is".length());

                GetterSetterPair getterSettingPair = getterSetterMapping.get(objectName);
                if (getterSettingPair == null) {
                    getterSettingPair = new GetterSetterPair();
                    getterSetterMapping.put(objectName, getterSettingPair);
                }
                getterSettingPair.setGetter(method);
            }
        }

        /*
         * Found all our mappings. Now call the getter and setter or set the field via reflection and call the getting
         * it doesn't have a setter.
         */
        for (final Entry<String, GetterSetterPair> entry : getterSetterMapping.entrySet()) {
            final GetterSetterPair pair = entry.getValue();

            final String objectName = entry.getKey();
            final String fieldName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);

            if (pair.hasGetterAndSetter()) {
                /* Create an object. */
                final Class<?> parameterType = pair.getSetter().getParameterTypes()[0];
                final Object newObject = createObject(fieldName, parameterType);

                pair.getSetter().invoke(instance, newObject);

                callGetter(fieldName, pair.getGetter(), instance, newObject);
            } else if (pair.getGetter() != null) {
                /*
                 * Object is immutable (no setter but Hibernate or something else sets it via reflection). Use
                 * reflection to set object and verify that same object is returned when calling the getter.
                 */
                final Object newObject = createObject(fieldName, pair.getGetter().getReturnType());
                final Optional<Field> optionalField = fields.stream()
                        .filter(field -> field.getName().equals(fieldName))
                        .findFirst();
                if (optionalField.isEmpty()) {
                    throw new FileNotFoundException(fieldName);
                }
                final Field field = optionalField.get();
                field.setAccessible(true);
                field.set(instance, newObject);

                callGetter(fieldName, pair.getGetter(), instance, newObject);
            }
        }
    }

}