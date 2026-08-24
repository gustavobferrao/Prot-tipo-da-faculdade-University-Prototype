package conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "siac";
    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }


    public static void close() {
        factory.close();
    }
}
