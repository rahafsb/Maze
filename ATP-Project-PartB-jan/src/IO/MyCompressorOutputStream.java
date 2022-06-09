package IO;

import java.io.*;

public class MyCompressorOutputStream extends java.io.OutputStream{
    public OutputStream out;

    public MyCompressorOutputStream(OutputStream o){
        if(o != null){
            this.out = o;
        }
    }


    @Override
    public void write(int b) {

    }

    @Override
    public void write(byte[] b) throws IOException {
        if(b.length < 24){
            try {
                throw new Exception("Cannot build maze with less than 24 bits");
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        int maze = b.length-24;
        int size = ((maze-(maze%7))/7+maze%7)+24;
        byte[] ret = new byte[size];
        for(int i =0; i< 24; i++){
            ret[i] =b[i];
        }
        int max = (maze-(maze%7))/7;
        int count =0;
        String bin = "";
        for( int i=24; i<( maze-(maze%7))+25; i++){//25
            if(i-24 == 0){
                bin +=b[i];
                continue;
            }
            if((i-24)%7 != 0 && count < max){
                bin += b[i];
            }
            if((i-24)%7 == 0){
                ret[24+count] = (byte)Integer.parseInt(bin,2);
                count++;
                bin = "";
                if (i < b.length){
                    bin += b[i];
                }
            }
        }
        for(int i= 0; i< maze%7;i++){
            ret[count+24] = (byte)(b[24+max*7 +i]-2);
            count++;
        }
        out.write(ret);
    }

    public static void main(String[] args) {
        byte[] b = {127};
        try {
            // craete a new input stream
            InputStream is = new FileInputStream("take.txt");

            // read what we wrote
            for (int i = 0; i < 32; i++) {
                int j = is.read();
                System.out.println("in the " + i +" iteration: "+j);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
/**/