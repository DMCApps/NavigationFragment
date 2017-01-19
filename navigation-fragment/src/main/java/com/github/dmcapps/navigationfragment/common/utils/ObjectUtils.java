package com.github.dmcapps.navigationfragment.common.utils;

/**
 * Created by DCarmo on 16-03-07.
 */
public final class ObjectUtils {
    private static final String TAG = ObjectUtils.class.getSimpleName();

    private ObjectUtils() { }

    /**
     * This is a seudo method of the C# as keyword. It will check the type of obj and cast to the appropriate class if it is that type or return null it not.
     *
     * @param
     *      obj -> The object to be checked with as
     * @param
     *      asClass -> The class type we are checking obj against
     * @param
     *      <T> -> The type of class that we anticipate returning
     * @return
     *      The object casted to the given class or<br />
     *      null if it's not that type of class
     */
    public static <T> T as(Class<T> asClass, Object obj) {
        return asClass.isInstance(obj) ? asClass.cast(obj) : null;
    }
}
