package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PrimaryUser {
    private int id_user;
    private String name;
    private String email;
    private String address;
    private String contact;
    private String birth_date;

    public PrimaryUser(int id_user, String name, String email, String address, String contact, String birth_date) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.birth_date = birth_date;
    }

    public PrimaryUser(String name, String email, String address, String contact, String birth_date) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.contact = contact;
        this.birth_date = birth_date;
    }

    public PrimaryUser(int idUser) {
        this.id_user = idUser;
    }

    public int getId_user() {
        return id_user;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public static class LegalEntityInstitute extends PrimaryUser{
        int id_institute;
        String cnpj;

        public LegalEntityInstitute(int id_user, String cnpj, int id_institute) {
            super(id_user);
            this.id_institute = id_institute;
            this.cnpj = cnpj;
        }

        public int getId_institute() {
            return id_institute;
        }

        public String getCnpj() {
            return cnpj;
        }
    }

    public static class NaturalPerson extends PrimaryUser{
        String cpf;

        public NaturalPerson(int idUser, String cpf) {
            super(idUser);
            this.cpf = cpf;
        }

        public String getCpf() {
            return cpf;
        }
    }
}