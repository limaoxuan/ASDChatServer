package dataaccess;

import dao.User;
import utility.OSinfo;

import java.awt.print.Book;
import java.io.*;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataAccessFacade implements DataAccess {




    enum StorageType {
        USERS
    }


//    public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";
    public static final String OUTPUT_DIR1 = System.getProperty("user.dir")
            + (OSinfo.isMacOSX() ? "/src/dataaccess/storage" : "\\src\\dataaccess\\storage");

//    public static final String OUTPUT_DIR = System.getProperty("user.dir");
//    public static final String OUTPUT_DIR1 = System.getProperty("user.dir");

    public static final String DATE_PATTERN = "MM/dd/yyyy";



    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserList() {
        return  (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    public void addUser(User user) {
        HashMap<String, User> users = readUserList();
        String userId = user.getId();
        if (users!=null)
            users.put(userId, user);
        else
            users = new HashMap<String, User>();
        saveToStorage(StorageType.USERS, users);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {

            FileOutputStream fos = new FileOutputStream(OUTPUT_DIR1 + type.toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(ob);
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {

            FileInputStream is = new FileInputStream(OUTPUT_DIR1 + type.toString());

            ObjectInputStream objectInputStream = new ObjectInputStream(is);
            retVal = objectInputStream.readObject();
            objectInputStream.close();
            ;


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

//    final static class Pair<S, T> implements Serializable {
//
//        S first;
//        T second;
//
//        Pair(S s, T t) {
//            first = s;
//            second = t;
//        }
//
//        @Override
//        public boolean equals(Object ob) {
//            if (ob == null)
//                return false;
//            if (this == ob)
//                return true;
//            if (ob.getClass() != getClass())
//                return false;
//            @SuppressWarnings("unchecked")
//            Pair<S, T> p = (Pair<S, T>) ob;
//            return p.first.equals(first) && p.second.equals(second);
//        }
//
//        @Override
//        public int hashCode() {
//            return first.hashCode() + 5 * second.hashCode();
//        }
//
//        @Override
//        public String toString() {
//            return "(" + first.toString() + ", " + second.toString() + ")";
//        }
//
//        private static final long serialVersionUID = 5399827794066637059L;
//    }
}
