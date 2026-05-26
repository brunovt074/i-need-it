package com.brunovt.ineedit.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EntryDao_Impl implements EntryDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final EntityUpsertionAdapter<EntryEntity> __upsertionAdapterOfEntryEntity;

  public EntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM entries WHERE id = ?";
        return _query;
      }
    };
    this.__upsertionAdapterOfEntryEntity = new EntityUpsertionAdapter<EntryEntity>(new EntityInsertionAdapter<EntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `entries` (`id`,`column`,`name`,`timeKey`,`specificDate`,`costAmountMinor`,`costCurrency`,`place`,`tags`,`statusId`,`actions`,`completedAt`,`createdAt`,`updatedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EntryEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getColumn());
        statement.bindString(3, entity.getName());
        if (entity.getTimeKey() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTimeKey());
        }
        if (entity.getSpecificDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSpecificDate());
        }
        if (entity.getCostAmountMinor() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCostAmountMinor());
        }
        if (entity.getCostCurrency() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCostCurrency());
        }
        if (entity.getPlace() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPlace());
        }
        statement.bindString(9, entity.getTags());
        if (entity.getStatusId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getStatusId());
        }
        statement.bindString(11, entity.getActions());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCompletedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
      }
    }, new EntityDeletionOrUpdateAdapter<EntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `entries` SET `id` = ?,`column` = ?,`name` = ?,`timeKey` = ?,`specificDate` = ?,`costAmountMinor` = ?,`costCurrency` = ?,`place` = ?,`tags` = ?,`statusId` = ?,`actions` = ?,`completedAt` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EntryEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getColumn());
        statement.bindString(3, entity.getName());
        if (entity.getTimeKey() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTimeKey());
        }
        if (entity.getSpecificDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSpecificDate());
        }
        if (entity.getCostAmountMinor() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCostAmountMinor());
        }
        if (entity.getCostCurrency() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCostCurrency());
        }
        if (entity.getPlace() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPlace());
        }
        statement.bindString(9, entity.getTags());
        if (entity.getStatusId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getStatusId());
        }
        statement.bindString(11, entity.getActions());
        if (entity.getCompletedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCompletedAt());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        statement.bindString(15, entity.getId());
      }
    });
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsert(final EntryEntity e, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfEntryEntity.upsert(e);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EntryEntity>> observeActive(final String col) {
    final String _sql = "SELECT * FROM entries WHERE completedAt IS NULL AND `column` = ? ORDER BY updatedAt DESC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, col);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"entries"}, new Callable<List<EntryEntity>>() {
      @Override
      @NonNull
      public List<EntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfColumn = CursorUtil.getColumnIndexOrThrow(_cursor, "column");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTimeKey = CursorUtil.getColumnIndexOrThrow(_cursor, "timeKey");
          final int _cursorIndexOfSpecificDate = CursorUtil.getColumnIndexOrThrow(_cursor, "specificDate");
          final int _cursorIndexOfCostAmountMinor = CursorUtil.getColumnIndexOrThrow(_cursor, "costAmountMinor");
          final int _cursorIndexOfCostCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "costCurrency");
          final int _cursorIndexOfPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "place");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<EntryEntity> _result = new ArrayList<EntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EntryEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpColumn;
            _tmpColumn = _cursor.getString(_cursorIndexOfColumn);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTimeKey;
            if (_cursor.isNull(_cursorIndexOfTimeKey)) {
              _tmpTimeKey = null;
            } else {
              _tmpTimeKey = _cursor.getString(_cursorIndexOfTimeKey);
            }
            final String _tmpSpecificDate;
            if (_cursor.isNull(_cursorIndexOfSpecificDate)) {
              _tmpSpecificDate = null;
            } else {
              _tmpSpecificDate = _cursor.getString(_cursorIndexOfSpecificDate);
            }
            final Long _tmpCostAmountMinor;
            if (_cursor.isNull(_cursorIndexOfCostAmountMinor)) {
              _tmpCostAmountMinor = null;
            } else {
              _tmpCostAmountMinor = _cursor.getLong(_cursorIndexOfCostAmountMinor);
            }
            final String _tmpCostCurrency;
            if (_cursor.isNull(_cursorIndexOfCostCurrency)) {
              _tmpCostCurrency = null;
            } else {
              _tmpCostCurrency = _cursor.getString(_cursorIndexOfCostCurrency);
            }
            final String _tmpPlace;
            if (_cursor.isNull(_cursorIndexOfPlace)) {
              _tmpPlace = null;
            } else {
              _tmpPlace = _cursor.getString(_cursorIndexOfPlace);
            }
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final String _tmpStatusId;
            if (_cursor.isNull(_cursorIndexOfStatusId)) {
              _tmpStatusId = null;
            } else {
              _tmpStatusId = _cursor.getString(_cursorIndexOfStatusId);
            }
            final String _tmpActions;
            _tmpActions = _cursor.getString(_cursorIndexOfActions);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new EntryEntity(_tmpId,_tmpColumn,_tmpName,_tmpTimeKey,_tmpSpecificDate,_tmpCostAmountMinor,_tmpCostCurrency,_tmpPlace,_tmpTags,_tmpStatusId,_tmpActions,_tmpCompletedAt,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Flow<List<EntryEntity>> observeDone() {
    final String _sql = "SELECT * FROM entries WHERE completedAt IS NOT NULL ORDER BY completedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"entries"}, new Callable<List<EntryEntity>>() {
      @Override
      @NonNull
      public List<EntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfColumn = CursorUtil.getColumnIndexOrThrow(_cursor, "column");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTimeKey = CursorUtil.getColumnIndexOrThrow(_cursor, "timeKey");
          final int _cursorIndexOfSpecificDate = CursorUtil.getColumnIndexOrThrow(_cursor, "specificDate");
          final int _cursorIndexOfCostAmountMinor = CursorUtil.getColumnIndexOrThrow(_cursor, "costAmountMinor");
          final int _cursorIndexOfCostCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "costCurrency");
          final int _cursorIndexOfPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "place");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<EntryEntity> _result = new ArrayList<EntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EntryEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpColumn;
            _tmpColumn = _cursor.getString(_cursorIndexOfColumn);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTimeKey;
            if (_cursor.isNull(_cursorIndexOfTimeKey)) {
              _tmpTimeKey = null;
            } else {
              _tmpTimeKey = _cursor.getString(_cursorIndexOfTimeKey);
            }
            final String _tmpSpecificDate;
            if (_cursor.isNull(_cursorIndexOfSpecificDate)) {
              _tmpSpecificDate = null;
            } else {
              _tmpSpecificDate = _cursor.getString(_cursorIndexOfSpecificDate);
            }
            final Long _tmpCostAmountMinor;
            if (_cursor.isNull(_cursorIndexOfCostAmountMinor)) {
              _tmpCostAmountMinor = null;
            } else {
              _tmpCostAmountMinor = _cursor.getLong(_cursorIndexOfCostAmountMinor);
            }
            final String _tmpCostCurrency;
            if (_cursor.isNull(_cursorIndexOfCostCurrency)) {
              _tmpCostCurrency = null;
            } else {
              _tmpCostCurrency = _cursor.getString(_cursorIndexOfCostCurrency);
            }
            final String _tmpPlace;
            if (_cursor.isNull(_cursorIndexOfPlace)) {
              _tmpPlace = null;
            } else {
              _tmpPlace = _cursor.getString(_cursorIndexOfPlace);
            }
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final String _tmpStatusId;
            if (_cursor.isNull(_cursorIndexOfStatusId)) {
              _tmpStatusId = null;
            } else {
              _tmpStatusId = _cursor.getString(_cursorIndexOfStatusId);
            }
            final String _tmpActions;
            _tmpActions = _cursor.getString(_cursorIndexOfActions);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new EntryEntity(_tmpId,_tmpColumn,_tmpName,_tmpTimeKey,_tmpSpecificDate,_tmpCostAmountMinor,_tmpCostCurrency,_tmpPlace,_tmpTags,_tmpStatusId,_tmpActions,_tmpCompletedAt,_tmpCreatedAt,_tmpUpdatedAt);
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

  @Override
  public Object byId(final String id, final Continuation<? super EntryEntity> $completion) {
    final String _sql = "SELECT * FROM entries WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EntryEntity>() {
      @Override
      @Nullable
      public EntryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfColumn = CursorUtil.getColumnIndexOrThrow(_cursor, "column");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTimeKey = CursorUtil.getColumnIndexOrThrow(_cursor, "timeKey");
          final int _cursorIndexOfSpecificDate = CursorUtil.getColumnIndexOrThrow(_cursor, "specificDate");
          final int _cursorIndexOfCostAmountMinor = CursorUtil.getColumnIndexOrThrow(_cursor, "costAmountMinor");
          final int _cursorIndexOfCostCurrency = CursorUtil.getColumnIndexOrThrow(_cursor, "costCurrency");
          final int _cursorIndexOfPlace = CursorUtil.getColumnIndexOrThrow(_cursor, "place");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfStatusId = CursorUtil.getColumnIndexOrThrow(_cursor, "statusId");
          final int _cursorIndexOfActions = CursorUtil.getColumnIndexOrThrow(_cursor, "actions");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final EntryEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpColumn;
            _tmpColumn = _cursor.getString(_cursorIndexOfColumn);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpTimeKey;
            if (_cursor.isNull(_cursorIndexOfTimeKey)) {
              _tmpTimeKey = null;
            } else {
              _tmpTimeKey = _cursor.getString(_cursorIndexOfTimeKey);
            }
            final String _tmpSpecificDate;
            if (_cursor.isNull(_cursorIndexOfSpecificDate)) {
              _tmpSpecificDate = null;
            } else {
              _tmpSpecificDate = _cursor.getString(_cursorIndexOfSpecificDate);
            }
            final Long _tmpCostAmountMinor;
            if (_cursor.isNull(_cursorIndexOfCostAmountMinor)) {
              _tmpCostAmountMinor = null;
            } else {
              _tmpCostAmountMinor = _cursor.getLong(_cursorIndexOfCostAmountMinor);
            }
            final String _tmpCostCurrency;
            if (_cursor.isNull(_cursorIndexOfCostCurrency)) {
              _tmpCostCurrency = null;
            } else {
              _tmpCostCurrency = _cursor.getString(_cursorIndexOfCostCurrency);
            }
            final String _tmpPlace;
            if (_cursor.isNull(_cursorIndexOfPlace)) {
              _tmpPlace = null;
            } else {
              _tmpPlace = _cursor.getString(_cursorIndexOfPlace);
            }
            final String _tmpTags;
            _tmpTags = _cursor.getString(_cursorIndexOfTags);
            final String _tmpStatusId;
            if (_cursor.isNull(_cursorIndexOfStatusId)) {
              _tmpStatusId = null;
            } else {
              _tmpStatusId = _cursor.getString(_cursorIndexOfStatusId);
            }
            final String _tmpActions;
            _tmpActions = _cursor.getString(_cursorIndexOfActions);
            final Long _tmpCompletedAt;
            if (_cursor.isNull(_cursorIndexOfCompletedAt)) {
              _tmpCompletedAt = null;
            } else {
              _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new EntryEntity(_tmpId,_tmpColumn,_tmpName,_tmpTimeKey,_tmpSpecificDate,_tmpCostAmountMinor,_tmpCostCurrency,_tmpPlace,_tmpTags,_tmpStatusId,_tmpActions,_tmpCompletedAt,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
