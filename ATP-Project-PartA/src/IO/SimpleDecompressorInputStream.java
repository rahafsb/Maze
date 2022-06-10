package IO;
import java.io.IOException;
import java.io.InputStream;

public class SimpleDecompressorInputStream  extends InputStream{
    InputStream in;

    public SimpleDecompressorInputStream(InputStream inp){
        if(inp != null){
            this.in = inp;
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
        int even = 0;
        while(read != -1 && i < b.length){
            if(i < 24){
                b[i] = (byte)read;
                i++;
                read = in.read();
                even++;
            }
            if(i >= 24 && even%2 ==0){
                if(read > 0){
                    for(int j=0; j < read; j++){
                        b[i] = 0;
                        i++;
                    }
                }
                read = in.read();
                even++;
            }
            if(i >= 24 && even%2 == 1){
                if(read != 0){
                    for(int j=0; j < read; j++){
                        b[i] = 1;
                        i++;
                    }
                }
                read = in.read();
                even++;
            }
        }
        return in.read(b);
    }
}
