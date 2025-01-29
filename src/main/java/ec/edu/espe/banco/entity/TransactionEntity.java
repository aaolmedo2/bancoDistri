package ec.edu.espe.banco.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transaccion")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaccion_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_cuenta_id", referencedColumnName = "cuenta_id")
    private AccountEntity account;

    @Column(name = "transasccion_tipo")
    private String type;

    @Column(name = "transaccion_descripcion")
    private String description;

    @Column(name = "transaccion_monto")
    private float amount;

    @Column(name = "transaccion_fecha")
    private Date date;

    public Integer getId() {
        return id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
