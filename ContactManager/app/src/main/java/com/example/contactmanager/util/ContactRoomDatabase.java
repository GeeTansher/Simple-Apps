package com.example.contactmanager.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contactmanager.data.ContactDAO;
import com.example.contactmanager.model.ContactRoom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ContactRoom.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {

    // function
    public abstract ContactDAO contactDAO();

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // if volatile it can be out of service or deleted if not used
    private static volatile ContactRoomDatabase INSTANCE;

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        ContactDAO contactDAO = INSTANCE.contactDAO();
                        contactDAO.deleteAll();

                        ContactRoom contactRoom = new ContactRoom("Geetansh", "Student");
                        contactDAO.insert(contactRoom);

                        contactRoom = new ContactRoom("Vikas Verma", "Job person");
                        contactDAO.insert(contactRoom);
                    });
                }
            };

    public static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            /*
             The synchronized keyword prevents concurrent access to a block of
             code or object by multiple threads.
            */
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //OR

    /*
    public static synchronized ContactRoomDatabase getDatabase(final Context context) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
*/


}
