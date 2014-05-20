
package BLL;

import Observer.IObserver;
import Observer.ISubject;
import java.util.ArrayList;

public class BLLError implements ISubject{
    
    private static BLLError m_instance;
    private final String ERROR_SYMBOL = "¤";
    private String error = ERROR_SYMBOL;
    private final ArrayList<IObserver> observers;
    
    private BLLError(){
        observers = new ArrayList<>();
    }
    
    public static BLLError getInstance(){
        if(m_instance == null){
            m_instance = new BLLError();
        }
        return m_instance;
    }
    
    public void createFiremanError(){
        error = "Kunne ikke tilføje ny brandmand";
        notifyObservers();
    }
    
    public void createVehicleError(){
        error = "Kunne ikke tilføje nyt køretøj";
    }
    
    public void createMaterialError(){
        error = "Kunne ikke tilføje nyt materiel";
    }
    
    public void deleteFiremanError(){
        error = "Kunne ikke slette brandmanden";
    }
    
    public void deleteVehicleError(){
        error = "Kunne ikke slette køretøjet";
    }
    
    public void deleteMaterialError(){
        error = "Kunne ikke slette materiellet";
    }
    
    public void updateFiremanError(){
        error = "Kunne ikke opdatere brandmanden";
    }
    
    public void updateVehicleError(){
        error = "Kunne ikke opdatere køretøjet";
    }
    
    public void updateMaterialError(){
        error = "Kunne ikke opdatere materiel";
    }
    
    public void readFiremenError(){
        error = "Kunne ikke læse alle brandmænd";
    }
    
    public void readVehiclesError(){
        error = "Kunne ikke læse alle køretøjer";
    }
    
    public void readMaterialError(){
        error = "Kunne ikke læse alt materiel";
    }
    
    public String getError(){
        return error;
    }
    
    public void resetError(){
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
        for (IObserver observer : observers){
            observer.update(error);
        }
    }
    
}
