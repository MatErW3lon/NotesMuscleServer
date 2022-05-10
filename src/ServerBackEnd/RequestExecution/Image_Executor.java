package ServerBackEnd.RequestExecution;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;
import ServerBackEnd.MainServer;
import ServerBackEnd.Notes_Builder;

/**
 * Image_Executor
 */
class Image_Executor extends Command_Executor{

    int imageCount; //default package access
    private Socket ocr_clientsocket;
    private DataInputStream ocr_inputStream;
    private DataOutputStream ocr_outputStream;
    private String notes_data;

    public Image_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
        imageCount = 1;
        notes_data = "";
        //initiailize
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
        //System.out.println("HERE IN IMAGE SEND");
        String dateOfReceive = incomingData[2];
        Notes_Builder notes_Builder = myClientHandler.getNotesBuilder();
        notes_Builder.setDate(dateOfReceive);
        if(!notes_Builder.createdNotesFile){
            notes_Builder.createNotesFile();
        }

        int imageDataLength = Integer.parseInt(incomingData[1]);
        byte[] imageBytes = new byte[imageDataLength];
        myClientHandler.getInputStream().readFully(imageBytes);
        init_ocr_connection(imageBytes, imageDataLength, notes_Builder);

        /*ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, "png", new File(System.getProperty("user.dir") + "\\ocr_image_buffer\\image" + imageCount + "_" + myClientHandler.getUserName() + ".png"));
        imageCount++;*/
        return true;
    }

    private void init_ocr_connection(byte[] img_bytes, int imageDataLength, Notes_Builder notes_Builder) throws UnknownHostException, IOException{
        //attempt handshake
        ocr_clientsocket = new Socket("localhost", MainServer.OCR_SERVER_PORT);
        ocr_inputStream = new DataInputStream(ocr_clientsocket.getInputStream());
        ocr_outputStream = new DataOutputStream(ocr_clientsocket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    ocr_outputStream.writeInt(imageDataLength);
                    ocr_outputStream.flush();
                    ocr_outputStream.write(img_bytes, 0, img_bytes.length);
                    ocr_outputStream.flush();

                    //now we wait for the response and build the notes data
                    notes_data = ocr_inputStream.readUTF();
                    System.out.println("NOTES DATA: " + notes_data);
                    notes_Builder.appendToFile(notes_data);
                }catch (IOException e) {
                    closeOcrConnection();
                    e.printStackTrace(System.err);
                }
            }
        }).start();
    }

    private void closeOcrConnection(){
        try{
            if(ocr_clientsocket != null){
                ocr_clientsocket.close();
                ocr_clientsocket = null;
            }
            if(ocr_inputStream != null){
                ocr_inputStream.close();
                ocr_inputStream = null;
            }
            if(ocr_outputStream != null){
                ocr_outputStream.close();
                ocr_outputStream = null;
            }
        }catch(IOException ioException){
            ioException.printStackTrace(System.err);
        }
    }
}