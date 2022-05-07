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

    public static synchronized ArrayList<String> ClientInformationHandler(String tempString, boolean setTextArea){
        if(setTextArea){
            return new ArrayList<String>(Client_Information);
        }else{
            Client_Information.add(tempString);
            return null;
        }
    }

    public static void resetClientInformation(){
        System.out.println("HERE");
        Client_Information = new ArrayList<>();
    }
}
