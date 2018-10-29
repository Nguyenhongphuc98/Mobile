package com.example.uitstark.dailys_notes.DTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public  class  Global {
   public static int idCurrentUser;

   public  static byte[] ConverttoByte(BirthDay birthDay){
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ObjectOutputStream os = null;
      try {
         os = new ObjectOutputStream(out);
      } catch (IOException e) {
         e.printStackTrace();
      }
      try {
         os.writeObject(birthDay);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return out.toByteArray();
   }

   public  static BirthDay ConverttoBirthday(byte[] data) throws IOException, ClassNotFoundException {
      ByteArrayInputStream in = new ByteArrayInputStream(data);
      ObjectInputStream is = null;
      try {
         is = new ObjectInputStream(in);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return (BirthDay) is.readObject();
   }
}
