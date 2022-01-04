package pt.ipp.isep.dei.esoft.pot.model.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pt.ipp.isep.dei.esoft.pot.model.Freelancer;
import pt.ipp.isep.dei.esoft.pot.model.PaymentTransaction;
import pt.ipp.isep.dei.esoft.pot.model.Task;

/**
 * The type Payment transaction list.
 *
 * @author Berkelios
 */
public class PaymentTransactionList implements Serializable {

    /**
     * The Payments List.
     */
    private final List<PaymentTransaction> m_ListPay;

    /**
     * Instantiates a new Payment transaction list.
     */
    public PaymentTransactionList() {
        this.m_ListPay = new ArrayList<>();
    }

    /**
     * Gets payment transactions.
     *
     * @return the payment transactions
     */
    public List<PaymentTransaction> getPaymentTransactions() {
        return this.m_ListPay;
    }

    /**
     * New payment transaction payment transaction.
     *
     * @param id    the id
     * @param freel the freel
     * @param task  the task
     * @return the payment transaction
     */
    public PaymentTransaction newPaymentTransaction(String id, Freelancer freel, Task task) {
        return new PaymentTransaction(id, freel, task);
    }

    /**
     * Register payment transaction boolean.
     *
     * @param m_oPaymentTransaction the m o payment transaction
     * @return the boolean
     */
    public boolean registerPaymentTransaction(PaymentTransaction m_oPaymentTransaction) {
        if (this.validatePaymentTransaction(m_oPaymentTransaction)) {
            return addPaymentTransaction(m_oPaymentTransaction);
        }
        return false;
    }

    /**
     * Validate payment transaction boolean.
     *
     * @param m_oPaymentTransaction the m o payment transaction
     * @return the boolean
     */
    public boolean validatePaymentTransaction(PaymentTransaction m_oPaymentTransaction) {
        if (this.m_ListPay.contains(m_oPaymentTransaction)) {
            throw new IllegalArgumentException("This Payment Transaction's ID already exists!");
        } else {
            return true;
        }
    }

    /**
     * Add payment transaction boolean.
     *
     * @param m_oPaymentTransaction the m o payment transaction
     * @return the boolean
     */
    public boolean addPaymentTransaction(PaymentTransaction m_oPaymentTransaction) {
        return this.m_ListPay.add(m_oPaymentTransaction);
    }
}
