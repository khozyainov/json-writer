package lesson8;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by entony on 11.09.17.
 */
public class JsonHelper {
    public static String toJson(Object obj){
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        return objToJson(null, obj, objBuilder).build().toString();
    }

    private static JsonObjectBuilder objToJson (String fieldName, Object obj, JsonObjectBuilder objBuilder){
        Class cls = obj.getClass();
        if (obj == null){
            return objBuilder.addNull(fieldName);
        } else {
            List<Field> fields = getFields(obj);
            for (Field field: fields){

                Object value = null;

                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Class type = field.getType();
                fieldName = field.getName();

                if (type.isPrimitive() || value instanceof String){
                    valueToJson(fieldName, value, objBuilder);
                } else if (type.isArray()){
                    objBuilder.add(fieldName, arrayToJson(value, Json.createArrayBuilder()));
                } else if (Collection.class.isAssignableFrom(type)){
                    Object[] collection = ((Collection) value ).toArray();
                    objBuilder.add(fieldName, arrayToJson(collection, Json.createArrayBuilder()));
                } else
                    objToJson(fieldName, value, objBuilder);
            }
        }
        return objBuilder;
    }


    private static void valueToJson (String fieldName, Object value, JsonObjectBuilder objBuilder) {
        Class type = value.getClass();
        if (value instanceof Byte) {
            objBuilder.add(fieldName, (byte) value);
        } else if (value instanceof Integer) {
            objBuilder.add(fieldName, (int) value);
        } else if (value instanceof Short) {
            objBuilder.add(fieldName, (short) value);
        } else if (value instanceof Long) {
            objBuilder.add(fieldName, (long) value);
        } else if (value instanceof Float) {
            objBuilder.add(fieldName, (float) value);
        } else if (value instanceof Double) {
            objBuilder.add(fieldName, (double) value);
        } else if (value instanceof Boolean) {
            objBuilder.add(fieldName, (boolean) value);
        } else if (value instanceof Character) {
            objBuilder.add(fieldName, (char) value);
        } else if (value instanceof String) {
            objBuilder.add(fieldName, (String) value);
        }
    }

    private static JsonArrayBuilder arrayToJson (Object value, JsonArrayBuilder arrayBuilder){
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            Object item = Array.get(value, i);
            if (item instanceof Byte) {
                arrayBuilder.add((byte) item);
            } else if (item instanceof Integer) {
                arrayBuilder.add((int) item);
            } else if (item instanceof Short) {
                arrayBuilder.add((short) item);
            } else if (item instanceof Long) {
                arrayBuilder.add((long) item);
            } else if (item instanceof Float) {
                arrayBuilder.add((float) item);
            } else if (item instanceof Double) {
                arrayBuilder.add((double) item);
            } else if (item instanceof Boolean) {
                arrayBuilder.add((boolean) item);
            } else if (item instanceof Character) {
                arrayBuilder.add((char) item);
            } else if (item instanceof String) {
                arrayBuilder.add((String) item);
            } else if (item.getClass().isArray()){
                arrayToJson(item, arrayBuilder);
            } else if (Collection.class.isAssignableFrom(item.getClass())){
                item = ((Collection) item).toArray();
                arrayBuilder.add(arrayToJson(item, Json.createArrayBuilder()));
            } else if (item instanceof Object){
                arrayBuilder.add(objToJson(null, item, Json.createObjectBuilder()));
            }
        }
        return arrayBuilder;
    }

    private static List<Field> getFields(Object o) {
        List<Field> fields = new ArrayList<Field>();
        fields = Arrays.asList(o.getClass().getDeclaredFields());
        return fields;
    }

}
