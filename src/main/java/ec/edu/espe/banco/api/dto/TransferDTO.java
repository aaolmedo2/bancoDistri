package ec.edu.espe.banco.api.dto;

import java.util.Date;

public class TransferDTO {

    private Integer sourceAccountId;

    private Integer targetAccountId;

    private float amount;

    private Date date;

    public Integer getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Integer getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Integer targetAccountId) {
        this.targetAccountId = targetAccountId;
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
