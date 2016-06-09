package com.bateeqshop.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

public class Query {
    public static int count (Class<? extends Model> c)
    {
        return new Select().from(c).count();
    }

    public static Model getSingle(Class<? extends Model> c)
    {
        return new Select().from(c).executeSingle();
    }

    public static List<Model> getAll(Class<? extends Model> c)
    {
        return new Select().from(c).execute();
    }

    public static void deleteAll(Class<? extends Model> c)
    {
        new Delete().from(c).execute();
    }

    public static void truncate(Class<? extends Model> c){
        TableInfo tableInfo = Cache.getTableInfo(c);
        ActiveAndroid.execSQL(
                String.format("DELETE FROM %s;",
                        tableInfo.getTableName()));
        ActiveAndroid.execSQL(
                String.format("DELETE FROM sqlite_sequence WHERE name='%s';",
                        tableInfo.getTableName()));
    }
}
