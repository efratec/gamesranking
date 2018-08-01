package br.com.games.gamesranking.util;

public class ObjectUtil {

    public static boolean isObjectNull(Object object) {
        return (object == null || object.equals(""));
    }

    public static boolean verifyNullOrEmptyObjects(Object... objects) {

        for (int i = 0; i < objects.length; i++) {
            if (!isObjectNull(objects[i])) {
                return true;
            }
        }
        return false;
    }

}
