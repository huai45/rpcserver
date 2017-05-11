package com.huai.rpc;

import java.io.*;
import java.util.Map;

/**
 * Created by zhonghuai.zhang on 2017/5/10.
 */
public class MyUtil {

    public static byte[] getByte(Object obj){
        byte[] b  = {};

        // ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
        ByteArrayInputStream BAIS = null;
        // 反序列化使用的输入流
        ObjectInputStream OIS = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            os.flush();
            os.close();
            b = bos.toByteArray();
            bos.close();

//            System.out.println("b:"+b);
//            System.out.println("b:"+b.toString());
//            System.out.println("b:"+new String(b));
//            System.out.println("b:"+new String(b,"utf8"));
//            System.out.println("b:"+new String(b,"gbk"));
//            System.out.println("b:"+new String(b,"utf-8"));

        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("序列化时产生错误 ");
            e.printStackTrace();
        }
        return b;
    }


    public static Map getMap(byte[] b){
        Map param = null;
        // ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
        ByteArrayInputStream BAIS = null;
        // 反序列化使用的输入流
        ObjectInputStream OIS = null;
        try {
            BAIS = new ByteArrayInputStream(b);
            OIS = new ObjectInputStream(BAIS);
            param = (Map) (OIS.readObject());
            System.out.println("反反序列化:" + param);
            OIS.close();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("反序列化时产生错误 ");
            e.printStackTrace();
        }
        return param;
    }

    public static Object getObject(byte[] b){
        Object param = null;
        // ByteArrayInputStream 可接收一个字节数组 "byte[] "。供反序列化做参数
        ByteArrayInputStream BAIS = null;
        // 反序列化使用的输入流
        ObjectInputStream OIS = null;
        try {
            BAIS = new ByteArrayInputStream(b);
            OIS = new ObjectInputStream(BAIS);
            param = OIS.readObject();
            System.out.println("反序列化:" + param);
            OIS.close();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("反序列化时产生错误 ");
            e.printStackTrace();
        }
        return param;
    }
}
