import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class input implements Runnable {
    private Socket s = null;
    private Boolean connection = true;
    private String filename = null;
    private FileWriter file = null;

    public input(Socket s, String a) throws IOException
    {
        this.s=s;
        filename="./chat/"+a;
        file=new FileWriter(filename);
    }

    public void exit()
    {
        connection=false;
    }

    @Override
    public void run() {
        try
        {
            file.write("input Started\n");
            InputStreamReader in=new InputStreamReader(s.getInputStream());
            BufferedReader bf=new BufferedReader(in);
            while(connection)
            {
                String str=bf.readLine();
                if(str!=null)
                {
                    file.write(str+"\n");
                    data.refresh=true;
                    System.out.println(str);
                }
                else{
                    exit();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
