package Users.Commands.Factory;
import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {

    private static final Map<String, UserCommandFactory> factories = new HashMap<>();

    static {
        factories.put("credentials", new CredentialsFactory());
        factories.put("order", new OrderFactory());
    }

    public static UserCommandFactory getFactory(String commandType) {
        UserCommandFactory factory = factories.get(commandType.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Tipo di comando non supportato: " + commandType);
        }
        return factory;
    }
}
