package se.andolf.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author thomas.andolf
 */
public class UriUtil {

    public static String extractLastPath(String uriAsString){
        URI uri;
        try {
            uri = new URI(uriAsString);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        final String path = uri.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
