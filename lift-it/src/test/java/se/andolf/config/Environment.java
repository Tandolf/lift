package se.andolf.config;

/**
 * @author Thomas on 2017-10-15.
 */
public enum Environment {
    LOCAL("localhost", 8080),
    CI("localhost", 8080);

    private final int port;
    private final String host;
    private String baseURI;

    Environment(String host, int port) {
        this.port = port;
        this.host = host;
        this.baseURI = "http://" + host;
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }

    public String baseURI() {
        return baseURI;
    }

    public static Environment current() {
        final String environment = System.getProperty("se.andolf.environment");
        if (environment == null) {
            return LOCAL;
        }
        return CI;
    }
}
