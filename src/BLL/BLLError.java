package BLL;

import Observer.IObserver;
import Observer.ISubject;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BLLError implements ISubject {

    private static BLLError m_instance;
    private final String ERROR_SYMBOL = "¤";
    private final String ERROR_HAS_HAPPEND = "Der er sket en fejl! - ";
    private final String ERROR_COULD_NOT = "Kunne ikke ";
    private String error = ERROR_SYMBOL;
    private final ArrayList<IObserver> observers;

    private BLLError() {
        observers = new ArrayList<>();
    }

    public static BLLError getInstance() {
        if (m_instance == null) {
            m_instance = new BLLError();
        }
        return m_instance;
    }

    public void createFiremanError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "tilføje ny brandmand";
        notifyObservers();
    }

    public void createVehicleError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "tilføje nyt køretøj";
        notifyObservers();
    }

    public void createMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "tilføje nyt materiel";
        notifyObservers();
    }

    public void deleteFiremanError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette brandmand";
        notifyObservers();
    }

    public void deleteVehicleError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette køretøj";
        notifyObservers();
    }

    public void deleteMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette materiel";
        notifyObservers();
    }

    public void updateFiremanError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere brandmand";
        notifyObservers();
    }

    public void updateVehicleError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere køretøj";
        notifyObservers();
    }

    public void updateMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere materiel";
        notifyObservers();
    }

    public void readFiremenError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alle brandmænd";
        notifyObservers();
    }

    public void readVehiclesError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alle køretøjer";
        notifyObservers();
    }

    public void readMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alt materiel";
        notifyObservers();
    }

    public void readAlarmError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alarm typerne";
        notifyObservers();
    }

    public void readZipcodeError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse postnumre";
        notifyObservers();
    }

    public void readRecentIncidentError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse nye meldinger";
        notifyObservers();
    }

    public void readIncidentDetailsError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse oplysninger på meldingerne";
        notifyObservers();
    }

    public void readUsageError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse forbrug på meldingerne";
        notifyObservers();
    }

    public void readRoleTimeError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse fremmødte på meldingerne";
        notifyObservers();
    }

    public void readIncidentsByDateError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse ønskede meldinger";
        notifyObservers();
    }

    public void emptyArrayList() {
        error = "Igen meldinger fundet";
        notifyObservers();
    }

    public void readLogoError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse logoet";
        notifyObservers();
    }

    public void createPDFError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "oprette dokumentet";
        notifyObservers();
    }

    public void updateDetailsError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere oplysningerne";
        notifyObservers();
    }

    public void finishIncidentError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "afslutte meldingen";
        notifyObservers();
    }

    public void fillOutDate() {
        error = ERROR_HAS_HAPPEND + "Datofeltet blev ikke udfyldt";
        notifyObservers();
    }

    public void fillOutZip() {
        error = ERROR_HAS_HAPPEND + "Postnummer blev ikke udfyldt";
        notifyObservers();
    }

    public String getError() {
        return error;
    }

    public void resetError() {
        error = ERROR_SYMBOL;
    }

    @Override
    public void register(IObserver o) {
        observers.add(o);
    }

    @Override
    public void unregister(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(error);
        }
    }

}
