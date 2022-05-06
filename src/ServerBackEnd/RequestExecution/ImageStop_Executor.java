package ServerBackEnd.RequestExecution;

import ServerBackEnd.ClientHandler;

class ImageStop_Executor extends Command_Executor{
    
    public ImageStop_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //here we can choose to perform image to text on the set of images
        //or choose not to in future implementations of the server
        System.out.println("HERE IN IMAGE STOP");
        return true;
    }

}
