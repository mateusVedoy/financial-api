package finances.api.application.service;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class SerializationService<T> {

    public byte[] serialize(List<T> list) {
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(list);
            objectOut.close();
            byteOut.close();
            return byteOut.toByteArray();
        }catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<T> deserialize(byte[] byteArr) {
        try{
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArr);
            ObjectInputStream objectIn = new ObjectInputStream(byteIn);
            List<T> objectsList = (List<T>) objectIn.readObject();
            objectIn.close();
            byteIn.close();
            return objectsList;
        }catch(IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
