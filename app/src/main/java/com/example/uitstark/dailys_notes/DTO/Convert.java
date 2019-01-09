package com.example.uitstark.dailys_notes.DTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public  class  Convert {
    public  static byte[] ConverttoByte(ToDo toDo){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.writeObject(toDo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public  static ToDo ConverttoToDo(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ToDo) is.readObject();
    }
}
