package Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import Structures.StructPerson;
import ir.ncis.phonebook.App;

public class Persons {
    public static String tableName = "persons";

    public static ArrayList<StructPerson> all() {
        return all(false);
    }

    public static ArrayList<StructPerson> all(boolean onlyFriends) {
        ArrayList<StructPerson> result = new ArrayList<>();
        Cursor cursor;
        if (!onlyFriends) {
            cursor = App.DB.rawQuery("SELECT * FROM \"" + tableName + "\"", null);
        } else {
            cursor = App.DB.rawQuery("SELECT * FROM \"" + tableName + "\" WHERE (\"friend\"=1)", null);
        }
        while (cursor.moveToNext()) {
            result.add(extract(cursor));
        }
        cursor.close();
        return result;
    }

    public static void clear() {
        App.DB.delete(tableName, "1", null);
    }

    public static boolean delete(int id) {
        return App.DB.delete(tableName, "\"id\"=?", new String[]{"" + id}) > 0;
    }

    public static boolean exists(int id) {
        return one(id) != null;
    }

    public static boolean insert(StructPerson person) {
        ContentValues values = getContentValues(person);
        return App.DB.insert(tableName, null, values) != -1;
    }

    public static StructPerson one(int id) {
        StructPerson result = null;
        Cursor cursor = App.DB.rawQuery("SELECT * FROM \"" + tableName + "\" WHERE (\"id\"=?)", new String[]{"" + id});
        if (cursor.moveToNext()) {
            result = extract(cursor);
        }
        cursor.close();
        return result;
    }

    public static boolean save(StructPerson person) {
        if (!exists(person.id)) {
            return insert(person);
        }
        return update(person);
    }

    public static boolean update(StructPerson person) {
        ContentValues values = getContentValues(person);
        return App.DB.update(tableName, values, "\"id\"=?", new String[]{"" + person.id}) > 0;
    }

    @NonNull
    private static ContentValues getContentValues(StructPerson person) {
        ContentValues values = new ContentValues();
        if (person.id != 0) {
            values.put("id", person.id);
        }
        values.put("name", person.name);
        values.put("mobile", person.mobile);
        values.put("address", person.address);
        values.put("male", person.male ? 1 : 0);
        values.put("friend", person.friend ? 1 : 0);
        values.put("score", person.score);
        values.put("stored", person.stored ? 1 : 0);
        return values;
    }

    private static StructPerson extract(Cursor cursor) {
        StructPerson result = new StructPerson();
        result.id = cursor.getInt(cursor.getColumnIndex("id"));
        result.name = cursor.getString(cursor.getColumnIndex("name"));
        result.mobile = cursor.getString(cursor.getColumnIndex("mobile"));
        result.address = cursor.getString(cursor.getColumnIndex("address"));
        result.male = cursor.getInt(cursor.getColumnIndex("male")) == 1;
        result.friend = cursor.getInt(cursor.getColumnIndex("friend")) == 1;
        result.score = cursor.getFloat(cursor.getColumnIndex("score"));
        result.stored = cursor.getInt(cursor.getColumnIndex("stored")) == 1;
        return result;
    }
}
