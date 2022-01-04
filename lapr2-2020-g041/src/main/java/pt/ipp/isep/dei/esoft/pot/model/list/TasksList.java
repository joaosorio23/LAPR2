package pt.ipp.isep.dei.esoft.pot.model.list;

import pt.ipp.isep.dei.esoft.pot.model.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tasks List.
 *
 * @author Berkelios
 */
public class TasksList implements Serializable {

    /**
     * The Task List.
     */
    private List<Task> m_TaskLst;

    /**
     * Instantiates a new Tasks List.
     */
    public TasksList() {
        this.m_TaskLst = new ArrayList<>();
    }

    /**
     * Gets Tasks.
     *
     * @return the Tasks
     */
    public List<Task> getTasks() {
        return this.m_TaskLst;
    }

    /**
     * New Task.
     *
     * @param id       the ID
     * @param desc     the Description
     * @param duration the Duration
     * @param cost     the Cost per Hour
     * @param cat      the Category
     * @return the Task
     */
    public Task newTask(String id, String desc, String duration, String cost, String cat) {

        return new Task(id, desc, duration, cost, cat);
    }

    /**
     * Register Task.
     *
     * @param m_oTask the Task
     * @return boolean boolean
     */
    public boolean registerTask(Task m_oTask) {
        if (this.validateTask(m_oTask)) {
            return addTask(m_oTask);
        }
        return false;
    }

    /**
     * Validate Task.
     *
     * @param m_oTask the Task
     * @return boolean boolean
     */
    public boolean validateTask(Task m_oTask) {

        if (this.m_TaskLst.contains(m_oTask)) {
            throw new IllegalArgumentException("Task's ID already exists!");
        }

        return true;
    }

    /**
     * Add Task.
     *
     * @param m_oTask the Task
     * @return boolean boolean
     */
    public boolean addTask(Task m_oTask) {
        return this.m_TaskLst.add(m_oTask);
    }

    /**
     * Gets Task by ID.
     *
     * @param taskID the Task ID
     * @return the Task
     */
    public Task getTaskById(String taskID) {
        for (Task task : m_TaskLst) {
            if (task.getID().equals(taskID)) {
                return task;
            }
        }
        throw new IllegalArgumentException("Task not found!");
    }
}
