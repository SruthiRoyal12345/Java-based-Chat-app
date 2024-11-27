import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{
    ServerSocket ss=null;
    Socket s=null;
    Vector<soc> soo=new Vector<>(0);
    private Boolean stop=true;
    private int currentfile;

    //filename=index of vector+1; 

    @Override
    public void run() {
        try{
            ss=new ServerSocket(49152);
            
            
        do
        {
            s=ss.accept();
            soc so=new soc();
            soo.add(so);
            data.connection.add(soo.size()+" Server ");  //this will add list of server as well as client
            String filename=""+data.connection.size();
            data.refresh=true;
            so.start(s, filename);
            File file=new File("./chat/"+filename);
            if(!file.createNewFile())
            {
                file.delete();
                file.createNewFile();
            }
        
        }while(stop);
        }

        catch(Exception e)
        {
            try {
                data.error.write("" + e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void start(int a)
    {
        soo.get(a-1).resume();
        currentfile=a;

    }

    public void exit() throws IOException
    {
        // stop=false;
        // ss.close();
        // s.close();
        System.out.println("ok");

       
    }

    public void text(String a)
    {
        soo.get(currentfile).text(a);
    }
}


class soc
{
    Thread t1=null;
    Thread t2=null;
    output o=null;
    input i=null;
    public void start(Socket s,String filename) throws IOException, InterruptedException
    {
        

        o=new output(s,filename);
        i=new input(s, filename);
        t1=new Thread(o);
        t2=new Thread(i);
        t1.start();
        t2.start();
        pause();
    }

    public void pause() throws InterruptedException
    {
        t1.wait();
    }

    public void resume()
    {
        t1.notify();
    }

    public void text(String a)
    {
        o.Text(a);
    }
}