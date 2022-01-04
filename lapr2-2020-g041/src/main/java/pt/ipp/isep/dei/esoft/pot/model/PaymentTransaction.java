package pt.ipp.isep.dei.esoft.pot.model;

import java.io.Serializable;

/**
 * The type Payment transaction.
 *
 * @author Berkelios
 */
public class PaymentTransaction implements Serializable {

    private String m_strId;
    private Freelancer m_oFreelancer;
    private Task m_oTask;

    /**
     * Instantiates a new Payment transaction.
     *
     * @param m_strId       the m str id
     * @param m_oFreelancer the m o freelancer
     * @param m_oTask       the m o task
     */
    public PaymentTransaction(String m_strId, Freelancer m_oFreelancer, Task m_oTask) {
        setId(m_strId);
        this.m_oFreelancer = m_oFreelancer;
        this.m_oTask = m_oTask;
    }

    /**
     * Sets id.
     *
     * @param m_strId the m str id
     */
    public void setId(String m_strId) {
        if (m_strId == null || m_strId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid ID!");
        }
        this.m_strId = m_strId;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public double getValue() {
        return this.m_oTask.getCost() * this.m_oTask.getDuration() * this.m_oFreelancer.getLevel().bonus;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return this.m_strId;
    }

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() {
        return this.m_oTask;
    }

    /**
     * Gets freelancer.
     *
     * @return the freelancer
     */
    public Freelancer getFreelancer() {
        return this.m_oFreelancer;
    }

    /**
     * Has task boolean.
     *
     * @return the boolean
     */
    public boolean hasTask() {
        return this.m_oTask != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentTransaction)) {
            return false;
        }
        PaymentTransaction that = (PaymentTransaction) o;
        return m_strId.equals(that.m_strId);
    }

    @Override
    public String toString() {
        return String.format("ID: %s - Freelancer: ID: %s , Name: %s - Task: %s - Task Execution: %s", this.m_strId, this.m_oFreelancer.getId(), this.m_oFreelancer.getName(), this.m_oTask, this.m_oTask.getTaskExecution().toString());
    }
}
