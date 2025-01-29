package ec.edu.espe.banco.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transferencia")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transferencia_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_cuenta_origen_id", referencedColumnName = "cuenta_id")
    private AccountEntity sourceAccount;

    @ManyToOne
    @JoinColumn(name = "fk_cuenta_destin_id", referencedColumnName = "cuenta_id")
    private AccountEntity targetAccount;

    @Column(name = "transferencia_monto")
    private float amount;

    @Column(name = "transferencia_fecha")
    private Date date;

    public Integer getId() {
        return id;
    }

    public AccountEntity getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(AccountEntity sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public AccountEntity getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(AccountEntity targetAccount) {
        this.targetAccount = targetAccount;
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
