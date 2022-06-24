package de.schildbach.wallet.addressbook;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AddressBookDao_Impl implements AddressBookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AddressBookEntry> __insertionAdapterOfAddressBookEntry;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public AddressBookDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAddressBookEntry = new EntityInsertionAdapter<AddressBookEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `address_book` (`address`,`label`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AddressBookEntry value) {
        if (value.getAddress() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getAddress());
        }
        if (value.getLabel() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLabel());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM address_book WHERE address = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertOrUpdate(final AddressBookEntry addressBookEntry) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAddressBookEntry.insert(addressBookEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final String address) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    if (address == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, address);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public String resolveLabel(final String address) {
    final String _sql = "SELECT label FROM address_book WHERE address = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (address == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, address);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final String _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getString(0);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<AddressBookEntry> get(final String constraint) {
    final String _sql = "SELECT * FROM address_book WHERE address LIKE '%' || ? || '%' OR label LIKE '%' || ? || '%' ORDER BY label COLLATE LOCALIZED ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (constraint == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, constraint);
    }
    _argIndex = 2;
    if (constraint == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, constraint);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
      final List<AddressBookEntry> _result = new ArrayList<AddressBookEntry>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final AddressBookEntry _item;
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpLabel;
        _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
        _item = new AddressBookEntry(_tmpAddress,_tmpLabel);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<AddressBookEntry>> getAll() {
    final String _sql = "SELECT * FROM address_book ORDER BY label COLLATE LOCALIZED ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"address_book"}, false, new Callable<List<AddressBookEntry>>() {
      @Override
      public List<AddressBookEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final List<AddressBookEntry> _result = new ArrayList<AddressBookEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AddressBookEntry _item;
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            _item = new AddressBookEntry(_tmpAddress,_tmpLabel);
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
  public LiveData<List<AddressBookEntry>> getAllExcept(final Set<String> except) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT ");
    _stringBuilder.append("*");
    _stringBuilder.append(" FROM address_book WHERE address NOT IN (");
    final int _inputSize = except.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(") ORDER BY label COLLATE LOCALIZED ASC");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : except) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"address_book"}, false, new Callable<List<AddressBookEntry>>() {
      @Override
      public List<AddressBookEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final List<AddressBookEntry> _result = new ArrayList<AddressBookEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AddressBookEntry _item_1;
            final String _tmpAddress;
            _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            final String _tmpLabel;
            _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            _item_1 = new AddressBookEntry(_tmpAddress,_tmpLabel);
            _result.add(_item_1);
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
}
