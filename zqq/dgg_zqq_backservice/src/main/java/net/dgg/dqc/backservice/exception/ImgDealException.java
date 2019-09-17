package net.dgg.dqc.backservice.exception;

import net.fblock.core.exception.BaseException;

/**
 * @author 刘阳
 * @ClassName <ImgDealException>
 * @despriction
 * @create 2018/08/15 9:42
 **/
public class ImgDealException extends BaseException {
    private String fastDfsPath;

    public ImgDealException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImgDealException(Throwable cause) {
        super(cause);
    }

    public ImgDealException(String message) {
        super(message);
    }

    public ImgDealException() {
        super();
    }

    public String getFastDfsPath() {
        return fastDfsPath;
    }

    public ImgDealException setFastDfsPath(String fastDfsPath) {
        this.fastDfsPath = fastDfsPath;
        return this;
    }
}
