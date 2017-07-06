package com.simple.findviewbyid;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 05/07/2017.
 */

public final class FindView {
    private static final String SUFFIX = "_FindView";

    private FindView() {}

    public static void bind(Activity activity) {
        try {
            Class bindingClass = Class.forName(activity.getClass().getCanonicalName() + SUFFIX);
            //noinspection unchecked
            Constructor constructor = bindingClass.getConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (InstantiationException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (InvocationTargetException e) {
            Log.e("TAG", "Meaningful Message", e);
        }
    }

    public static void bind(Object object, View view) {
        try {
            Class bindingClass = Class.forName(object.getClass().getPackage().getName() + "."  + object.getClass().getSimpleName() + SUFFIX);
            //noinspection unchecked
            Constructor constructor = bindingClass.getConstructor(object.getClass(), View.class);
            constructor.newInstance(object, view);
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (InstantiationException e) {
            Log.e("TAG", "Meaningful Message", e);
        } catch (InvocationTargetException e) {
            Log.e("TAG", "Meaningful Message", e);
        }
    }
}
