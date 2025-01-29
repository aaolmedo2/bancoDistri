package ec.edu.espe.banco.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cuenta")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "cliente_id")
    private ClientEntity client;

    @Column(name = "cuenta_numero")
    private String number;

    @Column(name = "cuenta_tipo")
    private String type;

    @Column(name = "cuenta_saldo")
    private float balance;

    @Column(name = "cuenta_fecha_creacion")
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
