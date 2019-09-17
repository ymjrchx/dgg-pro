package net.dgg.gspt.dqc.framework.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by leecheng on 2017/10/18.
 */
public class DelegateServletInputStream extends ServletInputStream {

    private ReadListener readListener;
    private int haveCount = 0;
    private int contentLength;
    private boolean isReady = true;

    public DelegateServletInputStream(InputStream inputStream, int contentLength) {
        this.contentLength = contentLength;
        this.inputStream = inputStream;
    }

    private InputStream inputStream;

    @Override
    public boolean isFinished() {
        return haveCount >= contentLength;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
    }

    @Override
    public int read() throws IOException {
        haveCount++;
        return inputStream.read();
    }

    /**
     * 从InputStream方法覆盖
     *
     * @param b
     * @param off
     * @param len
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        try {
            int readCnt = inputStream.read(b, off, len);
            haveCount += off + readCnt;
            return readCnt;
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public long skip(long n) throws IOException {
        haveCount += n;
        return inputStream.skip(n);
    }

    @Override
    public int read(byte[] b) throws IOException {
        int readCnt = inputStream.read(b);
        if (readCnt > 0)
            haveCount += readCnt;
        return readCnt;
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
        haveCount = 0;
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }
}
