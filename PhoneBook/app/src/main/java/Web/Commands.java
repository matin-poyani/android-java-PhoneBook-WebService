package Web;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Structures.StructPerson;

public class Commands {
    public static int create(StructPerson person) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "create");
        params.put("name", person.name);
        params.put("mobile", person.mobile);
        params.put("address", person.address);
        params.put("male", person.male ? "1" : "0");
        params.put("friend", person.friend ? "1" : "0");
        params.put("score", String.valueOf(person.score));
        String json = WebService.post(params);
        if (json != null) {
            try {
                JSONObject obj = new JSONObject(json);
                if (obj.getInt("code") == 1) {
                    return obj.getInt("data");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static ArrayList<StructPerson> read(int id) {
        ArrayList<StructPerson> result = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "read");
        if (id != 0) {
            params.put("id", String.valueOf(id));
        }
        String json = WebService.post(params);
        if (json != null) {
            try {
                JSONObject obj = new JSONObject(json);
                if (obj.getInt("code") == 1) {
                    JSONArray data = obj.getJSONArray("data");
                    int len = data.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject item = data.getJSONObject(i);
                        StructPerson person = new StructPerson();
                        person.id = item.getInt("id");
                        person.name = item.getString("name");
                        person.mobile = item.getString("mobile");
                        person.address = item.getString("address");
                        person.male = item.getInt("male") == 1;
                        person.friend = item.getInt("friend") == 1;
                        person.score = Float.parseFloat(item.getString("score"));
                        person.stored = true;
                        result.add(person);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean update(StructPerson person) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "update");
        params.put("id", String.valueOf(person.id));
        params.put("name", person.name);
        params.put("mobile", person.mobile);
        params.put("address", person.address);
        params.put("male", person.male ? "1" : "0");
        params.put("friend", person.friend ? "1" : "0");
        params.put("score", String.valueOf(person.score));
        String json = WebService.post(params);
        if (json != null) {
            try {
                return new JSONObject(json).getInt("code") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean delete(int id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "delete");
        params.put("id", String.valueOf(id));
        String json = WebService.post(params);
        if (json != null) {
            try {
                return new JSONObject(json).getInt("code") == 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
