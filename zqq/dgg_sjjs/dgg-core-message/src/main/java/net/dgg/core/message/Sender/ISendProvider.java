/**
 * FileName: ISendProvider
 * Author:   tumq
 * Date:     2018/12/13 9:47
 * Description: 发送消息提供者
 */
package net.dgg.core.message.Sender;

import net.dgg.core.message.common.exception.DggMessageExeption;
import net.dgg.core.message.entity.IMsgEntity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈发送消息提供者〉
 *
 * @author tumq
 * @create 2018/12/13
 */
public interface ISendProvider {
    void send(IMsgEntity entity) throws DggMessageExeption;
}
