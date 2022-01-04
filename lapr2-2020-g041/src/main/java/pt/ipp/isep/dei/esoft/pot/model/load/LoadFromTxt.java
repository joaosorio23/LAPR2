package pt.ipp.isep.dei.esoft.pot.model.load;

import pt.ipp.isep.dei.esoft.autorizacao.model.UserSession;
import pt.ipp.isep.dei.esoft.pot.controller.ApplicationPOT;
import pt.ipp.isep.dei.esoft.pot.model.*;
import pt.ipp.isep.dei.esoft.pot.model.enun.Country;
import pt.ipp.isep.dei.esoft.pot.model.enun.LevelOfExpertise;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import pt.ipp.isep.dei.esoft.pot.model.list.PaymentTransactionList;
import pt.ipp.isep.dei.esoft.pot.model.list.TasksList;
import pt.ipp.isep.dei.esoft.pot.model.register.FreelancersRegister;
import pt.ipp.isep.dei.esoft.pot.model.register.OrganizationsRegister;

/**
 * Loads a txt file from the computer.
 */
public class LoadFromTxt {

    /**
     * Load files.
     *
     * @param file the file
     * @throws FileNotFoundException the file not found exception
     */
    public static void loadFiles(File file) throws FileNotFoundException {
        try {
            ApplicationPOT app = ApplicationPOT.getInstance();
            UserSession session = app.getCurrentSession();
            Platform m_oPlatform = app.getPlatform();
            OrganizationsRegister or = m_oPlatform.getOrganizationsRegister();
            String strUserEmail = session.getUserEmail();
            Organization org = or.getOrganizationByUserEmail(strUserEmail);
            TasksList tl = org.getTasksList();
            FreelancersRegister fr = m_oPlatform.getFreelancersRegister();
            PaymentTransactionList payl = org.getPaymentTransactionList();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yy");
            Scanner ler = new Scanner(file);
            ler.nextLine();
            while (ler.hasNextLine()) {
                String line = ler.nextLine();
                String[] itens = line.split(" {4}");
                String transID = itens[0].trim();
                String taskID = itens[1].trim();
                String taskDescription = itens[2].trim();
                String taskDurationHours = itens[3].trim();
                String taskCostPerHour = itens[4].trim();
                String taskCategory = itens[5].trim();
                String executionFinishDate = itens[6].trim();
                LocalDate executionEnd = LocalDate.parse(executionFinishDate, dtf);
                String executionDelay = itens[7].trim();
                String executionDesc = itens[8].trim();
                String freeID = itens[9].trim();
                String freeName = itens[10].trim();
                LevelOfExpertise freeExp = LevelOfExpertise.setLevel(itens[11].trim());
                String freeEMail = itens[12].trim();
                String freeNIF = itens[13].trim();
                String freeBankAccount = itens[14].trim().substring(2, itens[14].length());
                Country country = Country.setCountry(itens[16].trim());
                Location freeLocation = new Location(itens[15].trim(), country);
                Freelancer free = new Freelancer(freeID, freeName, freeEMail, freeNIF, freeBankAccount, freeLocation, freeExp);
                Task task = new Task(taskID, taskDescription, taskDurationHours, taskCostPerHour, taskCategory);
                task.newTaskExecution(executionEnd, executionDelay, executionDesc);
                if (!(tl.getTasks().contains(task))) {
                    tl.addTask(task);
                }
                if (!(fr.getFreelancers().contains(free))) {
                    fr.addFreelancer(free);
                    PaymentTransaction pay = new PaymentTransaction(transID, free, task);
                    if (!(payl.getPaymentTransactions().contains(pay))) {
                        payl.addPaymentTransaction(pay);
                        free.setPayment(pay.getValue());
                    }

                } else {
                    Freelancer freelancer = fr.getFreelancerByID(free.getId());
                    PaymentTransaction pay = new PaymentTransaction(transID, freelancer, task);
                    if (!(payl.getPaymentTransactions().contains(pay))) {
                        payl.addPaymentTransaction(pay);
                        freelancer.setPayment(pay.getValue());
                    }
                }
            }
            ler.close();
        } catch (FileNotFoundException fnf) {
            throw new FileNotFoundException("Could not find the File!\nTryAgain!");
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException | NullPointerException nsee) {
            throw new NoSuchElementException("Wrong file format!");
        }

    }

}
