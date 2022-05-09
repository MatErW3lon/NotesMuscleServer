package ServerBackEnd;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Chat_Buffer implements Runnable{
    
    //the messages have to be flushed in the first in first out manner
    //the chat-buffer is buffered with messages until the client is ready to pull the messages

    private volatile Queue<String> messages_queue;
    private ClientHandler myClientHandler;
    private Thread messages_handler;

    public Chat_Buffer(ClientHandler myClientHandler){
        messages_queue = new LinkedList<String>();
        this.myClientHandler = myClientHandler;
        messages_handler = new Thread(this);
        messages_handler.start();
    }

    public void buffer_message(String message){
        messages_queue.add(message);
    }

    @Override
    public void run(){
        while(myClientHandler.flush_global_messages != ClientHandler.END_CHAT_BUFFER_THREAD){
            //System.out.println("runnning the buffer");
            if(myClientHandler.flush_global_messages == ClientHandler.FLUSH_MESSAGES){
                System.out.println("requested flush");
                try{
                    for(int i = 0; i < messages_queue.size(); i++){
                        String message = messages_queue.remove() + "\n";
                        myClientHandler.getOutStream().writeUTF(message);
                    }
                    myClientHandler.getOutStream().flush();
                }catch(IOException ioException){
                    System.err.println("An error occured in flushing messages to " + myClientHandler.getUserName());
                    ioException.printStackTrace(System.err);
                }
            }
        }
    }
}
