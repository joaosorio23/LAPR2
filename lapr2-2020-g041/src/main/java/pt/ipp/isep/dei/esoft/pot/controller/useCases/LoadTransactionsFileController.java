package pt.ipp.isep.dei.esoft.pot.controller.useCases;

import javafx.stage.FileChooser;
import pt.ipp.isep.dei.esoft.pot.model.load.LoadFromCsv;
import pt.ipp.isep.dei.esoft.pot.model.load.LoadFromTxt;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The type Load transactions file controller.
 */
public class LoadTransactionsFileController {

    /**
     * Read file.
     *
     * @param file the trim
     * @return true boolean
     * @throws FileNotFoundException the file not found exception
     */
    public boolean loadFileTxt(File file) throws FileNotFoundException {
        LoadFromTxt.loadFiles(file);
        return true;
    }

    /**
     * Load file csv boolean.
     *
     * @param file the file
     * @return the boolean
     * @throws FileNotFoundException the file not found exception
     */
    public boolean loadFileCsv(File file) throws FileNotFoundException {
        LoadFromCsv.loadFiles(file);
        return true;
    }

    /**
     * Filter type file chooser . extension filter.
     *
     * @param extensao the extensao
     * @return the file chooser . extension filter
     */
    public FileChooser.ExtensionFilter filterType(String extensao) {
        return new FileChooser.ExtensionFilter("Load File", extensao);
    }
}
