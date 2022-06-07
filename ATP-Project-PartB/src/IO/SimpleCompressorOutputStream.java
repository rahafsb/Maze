package IO;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class SimpleCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public SimpleCompressorOutputStream (OutputStream o){
        if(o != null){
            this.out = o;
        }

    }
    @Override
    public void write(int b) throws IOException {

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
        int zero = 0;
        int one = 0;
        ArrayList<Integer> curr = new ArrayList<Integer>();
        for(int i = 24; i <b.length; i++){
            byte at = b[i];

            if(zero < 127 && b[i] == 0){
                zero++;
            }
            if(one < 127 && b[i] ==1){
                one++;
            }
            if(one == 127){
                curr.add(zero);
                curr.add(one);
                one =0;
            }
            if(zero == 127){
                curr.add(zero);
                curr.add(one);
                zero=0;
            }

            if((at == 1 && i +1 < b.length&&b[i+1] != 1) ||(at == 1 &&  i +1 == b.length) ){
                curr.add(zero);
                curr.add(one);
                zero=0;
                one =0;
            }
            if(i +1 == b.length && b[i] == 0 ){
                curr.add(zero);
                curr.add(0);
                zero=0;
                one=0;
            }
        }
        byte[] ret = new byte[curr.size() +24];
        for(int i=0; i<curr.size() +24;i++){
            if(i < 24){
                ret[i] =b[i];
            }
            else{
                ret[i] = (byte)((int)curr.get(i - 24));
            }
        }
        out.write(ret);

    }


}
