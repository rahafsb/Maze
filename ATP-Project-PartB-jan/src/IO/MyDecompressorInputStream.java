package IO;
import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream{
    private InputStream in;

    public MyDecompressorInputStream(InputStream inp){
        if(inp != null){
            this.in=inp;
        }
    }
    @Override
    public int read() throws IOException {
        return 0;
    }
    @Override
    public int read(byte[] b) throws IOException {
        int i=0;
        int read = in.read();
        while(read != -1 && i < b.length){
            if(i < 24){
                b[i] = (byte)read;
                i++;
                read = in.read();
            }
            if(read >=0 && read != 255 && read != 254 && i >=24){
                String s = Integer.toBinaryString(read);
                if(s.length()<7){
                    s = zeros(7-s.length()) + s;
                }
                for(int j =0; j< 7; j++){
                    b[i] =(byte) (Integer.parseInt(""+s.charAt(j)));

                    i++;
                }
                read = in.read();

            }
            if(read == 255 && i>= 24){
                b[i] = 1;
                i++;
                if(i < b.length){
                    read = in.read();}
            }
            if(read == 254 && i>= 24){
                b[i]=0;
                i++;
                if(i < b.length){
                    read = in.read();}
            }
        }
        return in.read(b);
    }
    String zeros(int num){
        String ret ="";
        for(int i=0; i< num; i++){
            ret+="0";
        }
        return ret;
    }

    public static void main(String[] args) {
        //   System.out.println(""+Integer.toBinaryString(3));
        String s = "00000"+Integer.toBinaryString(3);
        System.out.println(s);
    }
}