package net.austechmc.discord.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtil {

    /**
     * Creates new instance of a class by calling a constructor that receives ctorClassArgs arguments.
     *
     * @link <a href="https://github.com/Alluxio/alluxio/blob/master/core/common/src/main/java/alluxio/util/CommonUtils.java#L279">...</a>
     * @param <T> type of the object
     * @param cls the class to create
     * @param args parameters type list of the constructor to initiate, if null default
     *        constructor will be called
     * @param conArgs the arguments to pass the constructor
     * @return new class object
     * @throws RuntimeException if the class cannot be instantiated
     */
    public static <T> T createNewClassInstance(Class<T> cls, Class<?>[] args, Object[] conArgs) {
        try {
            if (args == null) {
                return cls.getDeclaredConstructor().newInstance();
            }
            Constructor<T> con = cls.getConstructor(args);
            return con.newInstance(conArgs);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}