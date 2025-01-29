package ec.edu.espe.banco.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cliente")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer id;

    @Column(name = "cliente_nombre")
    private String name;

    @Column(name = "cliente_apellido")
    private String lastname;

    @Column(name = "cliente_email")
    private String email;

    @Column(name = "cliente_telefono")
    private String phone;

    @Column(name = "cliente_fecha_nacimiento")
    private Date birthDate;

    @Column(name = "cliente_direccion")
    private String address;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
