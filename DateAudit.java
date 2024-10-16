import java.io.Serializable;
import java.util.Date;

public class DateAudit implements Serializable {

    private Date createAt;
    private Date updatedAt;
    private Date handOverDate;
    private Date evictionDate;

    public DateAudit(Date createAt, Date updatedAt, Date handOverDate, Date evictionDate) {
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.handOverDate = handOverDate;
        this.evictionDate = evictionDate;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getHandOverDate() {
        return handOverDate;
    }

    public void setHandOverDate(Date handOverDate) {
        this.handOverDate = handOverDate;
    }

    public Date getEvictionDate() {
        return evictionDate;
    }

    public void setEvictionDate(Date evictionDate) {
        this.evictionDate = evictionDate;
    }
}