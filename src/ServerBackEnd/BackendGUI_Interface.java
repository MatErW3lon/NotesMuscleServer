package ServerBackEnd;

import java.util.ArrayList;

/**
 * THIS INTERFACE ALLOWS THE BACKEND TO INTERACT WITH THE GUI THREADS
 */
public class BackendGUI_Interface {

    private static ArrayList<String> Client_Information;

    static{
        Client_Information = new ArrayList<>();
    }

    public static synchronized ArrayList<String> ClientInformationHandler(String tempString, boolean setTextArea, boolean removeUser){
        if(setTextArea){
            return new ArrayList<String>(Client_Information);
        }else if(removeUser){
            Client_Information.remove(tempString);
            return new ArrayList<String>(Client_Information);
        }else{
            Client_Information.add(tempString);
            return null;
        }
    }
}
