package pt.ipp.isep.dei.esoft.pot.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Task.
 *
 * @author Berkelios
 */
public class Task implements Serializable {

    /**
     * The Task's ID.
     */
    private String m_strId;
    /**
     * The Task's Description.
     */
    private String m_strDesc;
    /**
     * The Task's Duration.
     */
    private double m_intDuration;
    /**
     * The Task's Cost per Hour.
     */
    private double m_douCost;
    /**
     * The Task's Category.
     */
    private String m_srtCategory;
    /**
     * The Task's Execution.
     */
    private TaskExecution m_oTaskEx;

    /**
     * Instantiates a new Task.
     *
     * @param strId       the ID
     * @param strDesc     the Description
     * @param intDuration the Duration
     * @param douCost     the Cost per Hour
     * @param srtCategory the Category
     */
    public Task(String strId, String strDesc, String intDuration, String douCost, String srtCategory) {
        setId(strId);
        setDesc(strDesc);
        setDuration(intDuration);
        setCost(douCost);
        setCategory(srtCategory);
    }

    /**
     * Sets ID.
     *
     * @param m_strId the ID
     */
    public void setId(String m_strId) {
        if (m_strId == null || m_strId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid ID!");
        }
        this.m_strId = m_strId;
    }

    /**
     * Sets Description.
     *
     * @param m_strDesc the Description
     */
    public void setDesc(String m_strDesc) {
        if (m_strDesc == null || m_strDesc.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Brief Description!");
        }
        this.m_strDesc = m_strDesc;
    }

    /**
     * Sets Category.
     *
     * @param m_srtCategory the Category
     */
    public void setCategory(String m_srtCategory) {
        if (m_srtCategory == null || m_srtCategory.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Category!");
        }
        this.m_srtCategory = m_srtCategory;
    }

    /**
     * New Task Execution.
     *
     * @param taskEnd      the Task End
     * @param taskDelay    the Task Delay
     * @param taskDescWork the Task Description Work
     */
    public void newTaskExecution(LocalDate taskEnd, String taskDelay, String taskDescWork) {
        this.m_oTaskEx = new TaskExecution(taskEnd, taskDelay, taskDescWork);
    }

    /**
     * Gets Duration.
     *
     * @return the Duration
     */
    public double getDuration() {
        return this.m_intDuration;
    }

    /**
     * Sets Duration.
     *
     * @param m_intDuration the Duration
     */
    public void setDuration(String m_intDuration) {
        if ((!m_intDuration.trim().isEmpty())) {
            try {
                this.m_intDuration = Double.parseDouble(m_intDuration);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Duration!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Duration!");
        }
    }

    /**
     * Gets Cost per Hour.
     *
     * @return the Cost per Hour
     */
    public double getCost() {
        return this.m_douCost;
    }

    /**
     * Sets Cost per Hour.
     *
     * @param m_douCost the Cost per Hour
     */
    public void setCost(String m_douCost) {
        if (!(m_douCost.trim().isEmpty())) {
            try {
                this.m_douCost = Double.parseDouble(m_douCost);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Invalid Cost per Hour!");
            }
        } else {
            throw new IllegalArgumentException("Invalid Cost per Hour!");
        }
    }

    /**
     * Gets ID.
     *
     * @return the ID
     */
    public String getID() {
        return this.m_strId;
    }

    /**
     * Gets Task Execution.
     *
     * @return the Task Execution
     */
    public TaskExecution getTaskExecution() {
        return this.m_oTaskEx;
    }

    /**
     * Checks if two Tasks objects are equal.
     *
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return m_strId.equals(task.m_strId);
    }

    /**
     * Textual Task's Description.
     *
     * @return Textual Task's Description
     */
    @Override
    public String toString() {
        return String.format("ID: %s , Brief Description: %s , Duration: %.2fh , Cost per Hour: %.2f€ , Category: %s", this.m_strId, this.m_strDesc, this.m_intDuration, this.m_douCost, this.m_srtCategory);
    }
}
