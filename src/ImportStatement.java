import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Total{
    private int total;
    public Total(){
        this.total = 0;
    }
    public int getTotal(){
        return total;
    }
    public void setTotal(int total){
        this.total = total;
    }

}
class ReadFile extends Thread {
    private String file;
    private File readfile;
    private Total t;
    private int imp = 0;

    public ReadFile(String file, Total t, ThreadGroup tg) {
        super(tg,file);
        this.file = file;
        this.t = t;
    }

    public void run() {

        String thisLine = null;
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            while ((thisLine = br.readLine()) != null) {

                if (thisLine.startsWith("import")) {
                    synchronized (t) {
                        t.setTotal(t.getTotal()+1);
                        imp += 1;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file + ":" + imp);
    }


}


public class ImportStatement {


    public static void main(String[] args) {
        Total t = new Total();

        ThreadGroup g1 = new ThreadGroup("g1");
        for(int x=0; x<= args.length -1; x++){
            new ReadFile(args[x],t,g1).start();
        }
        while (g1.activeCount()>0){
            System.out.print(g1.activeCount() + ",");
        }

        System.out.println("Total " + t.getTotal());

    }
}