import java.io.Serializable;
import java.time.LocalDate;

public class DateAudit implements Serializable {

    private LocalDate createAt;
    private LocalDate updatedAt;
    private LocalDate handOverDate;
    private LocalDate evictionDate;

    public DateAudit() {
        createAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    public DateAudit(LocalDate handOverDate, LocalDate evictionDate) {
        this.handOverDate = handOverDate;
        this.evictionDate = evictionDate;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getHandOverDate() {
        return handOverDate;
    }

    public void setHandOverDate(LocalDate handOverDate) {
        this.handOverDate = handOverDate;
    }

    public LocalDate getEvictionDate() {
        return evictionDate;
    }

    public void setEvictionDate(LocalDate evictionDate) {
        this.evictionDate = evictionDate;
    }

    @Override
    public String toString() {
        return "DateAudit [createAt=" + createAt + ", updatedAt=" + updatedAt + ", handOverDate=" + handOverDate
                + ", evictionDate=" + evictionDate + "]";
    }
}