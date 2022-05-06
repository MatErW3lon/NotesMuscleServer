package ServerBackEnd.RequestExecution;

import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

//import NetWorkProtocol.NetworkProtocol;
import ServerBackEnd.ClientHandler;

/**
 * Image_Executor
 */
class Image_Executor extends Command_Executor{

    static int imageCount; //default package access

    public Image_Executor(ClientHandler myClientHandler){
        super(myClientHandler);
        imageCount = 1;
    }

    @Override
    public boolean executeCommand(String[] incomingData) throws Exception{
       
        int imageDataLength = Integer.parseInt(incomingData[1]);
        byte[] imageBytes = new byte[imageDataLength];
        
        //myClientHandler.getOutStream().writeInt(NetworkProtocol.Image_Received_Confirmation);
        //myClientHandler.getOutStream().flush();
        
        myClientHandler.getInputStream().readFully(imageBytes);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bImage = ImageIO.read(bis);
        ImageIO.write(bImage, "png", new File(System.getProperty("user.dir") + "\\image" + imageCount + ".png"));
        imageCount++;
        return true;
    }

}