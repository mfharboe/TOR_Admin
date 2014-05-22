package BLL;

import Observer.IObserver;
import Observer.ISubject;
import java.util.ArrayList;

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
    }

    public void createMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "tilføje nyt materiel";
    }

    public void deleteFiremanError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette brandmand";
    }

    public void deleteVehicleError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette køretøj";
    }

    public void deleteMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "slette materiel";
    }

    public void updateFiremanError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere brandmand";
    }

    public void updateVehicleError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere køretøj";
    }

    public void updateMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere materiel";
    }

    public void readFiremenError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alle brandmænd";
    }

    public void readVehiclesError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alle køretøjer";
    }

    public void readMaterialError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alt materiel";
    }

    public void readAlarmError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse alarm typerne";
    }

    public void readZipcodeError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse postnumre";
    }

    public void readRecentIncidentError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse nye meldinger";
    }

    public void readIncidentDetailsError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse oplysninger på meldingerne";
    }

    public void readUsageError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse forbrug på meldingerne";
    }

    public void readRoleTimeError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse fremmødte på meldingerne";
    }

    public void emptyArrayList() {
        error = "Der er ingen nye meldinger";
    }
    
    public void readLogoError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "læse logoet";
    }
    
    public void createPDFError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "oprette dokumentet";
    }
    
    public void updateDetailsError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "opdatere detaljerne";
    }
    
    public void finishIncidentError() {
        error = ERROR_HAS_HAPPEND + ERROR_COULD_NOT + "afslutte meldingen";
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
