import Model.Aluno;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.ObjectInputFilter;

public class MainApp {
    public static void main(String[] args){
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Aluno.class);

        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Aluno aluno = new Aluno();
        aluno.setNome_aluno("Jorge");
        aluno.setIdade_aluno(21);
        aluno.setContato_aluno("519932787281");

        session.save(aluno);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
}
