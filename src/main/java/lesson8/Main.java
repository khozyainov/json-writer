package lesson8;

import lesson8.objects.ArrayExample;
import lesson8.objects.CollectionExample;
import lesson8.objects.PrimitivesExample;

/**
 * Created by entony on 09.09.17.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Primitives: " + JsonHelper.toJson(new PrimitivesExample()));
        System.out.println("Array: " + JsonHelper.toJson(new ArrayExample()));
        System.out.println("Collection: " + JsonHelper.toJson(new CollectionExample()));
    }
}
