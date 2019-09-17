package net.dgg.core.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Desc:   protostuff工具类
 * Author: Li Xingjiang
 * Date:   2018/9/14 14:45
 * Version: 1.0
 **/
public class DggProtostuffUtil {

    private static final Logger logger = LoggerFactory.getLogger(DggProtostuffUtil.class);

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        byte[] protoStuff = null;
        if (obj != null) {
            @SuppressWarnings("unchecked") Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
            LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
            try {
                protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
            } catch (Exception e) {
                logger.error("Failed to serializer, obj:{}", obj, e);
            } finally {
                buffer.clear();
            }
        }
        return protoStuff;
    }

    /**
     * 反序列化对象
     *
     * @param paramArrayOfByte
     * @param targetClass
     * @return
     */
    public static <T> T deserialize(byte[] paramArrayOfByte, Class<T> targetClass) {
        T instance = null;
        if (paramArrayOfByte != null && paramArrayOfByte.length > 0) {
            try {
                instance = targetClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("Failed to deserialize", e);
            }
            Schema<T> schema = RuntimeSchema.getSchema(targetClass);
            ProtostuffIOUtil.mergeFrom(paramArrayOfByte, instance, schema);
        }
        return instance;
    }

    /**
     * 序列化列表
     *
     * @param objList
     * @return
     */
    public static <T> byte[] serializeList(List<T> objList) {
        byte[] protoStuff = null;
        if (objList == null || objList.isEmpty()) {
            return protoStuff;
        }

        @SuppressWarnings("unchecked") Schema<T> schema =
                (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, objList, schema, buffer);
            protoStuff = bos.toByteArray();
        } catch (Exception e) {
            logger.error("Failed to serializer, obj list:{}", objList, e);
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return protoStuff;
    }

    /**
     * 反序列化列表
     *
     * @param paramArrayOfByte
     * @param targetClass
     * @return
     */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        List<T> resultList = null;
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            return resultList;
        }
        try {
            Schema<T> schema = RuntimeSchema.getSchema(targetClass);
            resultList = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            logger.error("Failed to deserialize", e);
        }
        return resultList;
    }

}
