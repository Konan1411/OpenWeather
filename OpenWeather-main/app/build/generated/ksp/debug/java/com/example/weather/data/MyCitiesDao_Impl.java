package com.example.weather.data;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyCitiesDao_Impl implements MyCitiesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MyCities> __insertionAdapterOfMyCities;

  private final EntityDeletionOrUpdateAdapter<MyCities> __deletionAdapterOfMyCities;

  public MyCitiesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMyCities = new EntityInsertionAdapter<MyCities>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `MyCities` (`city`,`timestamp`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyCities value) {
        if (value.getCity() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCity());
        }
        stmt.bindLong(2, value.getTimestamp());
      }
    };
    this.__deletionAdapterOfMyCities = new EntityDeletionOrUpdateAdapter<MyCities>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MyCities` WHERE `city` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MyCities value) {
        if (value.getCity() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getCity());
        }
      }
    };
  }

  @Override
  public Object insert(final MyCities city, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMyCities.insert(city);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object delete(final MyCities city, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMyCities.handle(city);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<MyCities>> getAllcities() {
    final String _sql = "SELECT * FROM MyCities ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"MyCities"}, new Callable<List<MyCities>>() {
      @Override
      public List<MyCities> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<MyCities> _result = new ArrayList<MyCities>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MyCities _item;
            final String _tmpCity;
            if (_cursor.isNull(_cursorIndexOfCity)) {
              _tmpCity = null;
            } else {
              _tmpCity = _cursor.getString(_cursorIndexOfCity);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new MyCities(_tmpCity,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
