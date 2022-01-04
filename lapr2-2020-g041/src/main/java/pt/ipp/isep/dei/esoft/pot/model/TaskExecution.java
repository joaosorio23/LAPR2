package pt.ipp.isep.dei.esoft.pot.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * The type Task execution.
 *
 * @author Berkelios
 */
public class TaskExecution implements Serializable {

    private Date m_dateDeadline;
    private double m_douDelay;
    private String m_strDescWork;

    /**
     * Instantiates a new Task execution.
     *
     * @param taskEnd      the task end
     * @param taskDelay    the task delay
     * @param taskDescWork the task description work
     */
    TaskExecution(LocalDate taskEnd, String taskDelay, String taskDescWork) {
        setEnd(taskEnd);
        setDelay(taskDelay);
        setDescWork(taskDescWork);
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return this.m_dateDeadline;
    }

    /**
     * Sets end.
     *
     * @param m_dateDeadline the m date deadline
     */
    public void setEnd(LocalDate m_dateDeadline) {
        if (m_dateDeadline == null) {
            throw new IllegalArgumentException("Invalid Date!");
        }
        if (m_dateDeadline.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid Date!");
        }
        this.m_dateDeadline = Date.from(m_dateDeadline.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Sets desc work.
     *
     * @param m_strDescWork the m str desc work
     */
    public void setDescWork(String m_strDescWork) {
        if (m_strDescWork == null || m_strDescWork.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Work Description!");
        }
        this.m_strDescWork = m_strDescWork;
    }

    /**
     * Gets delay.
     *
     * @return the delay
     */
    public double getDelay() {
        return this.m_douDelay;
    }

    /**
     * Sets delay.
     *
     * @param m_douDelay the m dou delay
     */
    public void setDelay(String m_douDelay) {
        if (!(m_douDelay.trim().isEmpty())) {
            try {
                this.m_douDelay = Double.parseDouble(m_douDelay);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Delay!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Delay!");
        }
    }

    @Override
    public String toString() {
        return String.format("Date: %s, Delay: %.2f, Work Description: %s", this.m_dateDeadline, this.m_douDelay, this.m_strDescWork);
    }
}
