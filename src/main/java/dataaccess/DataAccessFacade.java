package dataaccess;

import dao.User;
import utility.OSinfo;

import java.io.*;


import java.util.HashMap;

public class DataAccessFacade implements DataAccess {


    enum StorageType {
        USERS
    }

    public static final String OUTPUT_DIR1 = System.getProperty("user.dir")
            + (OSinfo.isMacOSX() ? "/src/main/java/dataaccess/storage/" : "\\src\\main\\java\\dataaccess\\storage\\");

    public static final String DATE_PATTERN = "MM/dd/yyyy";


    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserList() {
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    public boolean addUser(User user) {
        HashMap<String, User> users = readUserList();
        String userId = user.getUsername();
        if (users == null) {
            users = new HashMap<String, User>();
        }
        if (users.containsKey(userId)) {
            return false;
        }
        users.put(userId, user);
        saveToStorage(StorageType.USERS, users);
        return true;
    }

    public boolean checkLoginUser(User user) {
        HashMap<String, User> users = readUserList();
        if (!users.containsKey(user.getUsername())) {
            return false;
        }
        User current = users.get(user.getUsername());
        if (!current.getPassword().equals(user.getPassword())) {
            return false;

        }
        return true;
    }

    private void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {

            File file = new File(OUTPUT_DIR1 + type.toString());
            if (file.getParentFile().exists()) {
                file.getParentFile().mkdir();
                file.createNewFile();
            }


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

    private Object readFromStorage(StorageType type) {
        ObjectInputStream objectInputStream = null;
        Object retVal = null;
        try {

            FileInputStream is = new FileInputStream(OUTPUT_DIR1 + type.toString());

            objectInputStream = new ObjectInputStream(is);
            retVal = objectInputStream.readObject();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }


}
