package elegion.com.secondappforcontentprovidertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Button mButton;
    EditText mEditText;
    Spinner mSpinner1;
    Spinner mSpinner2;
    private String url;
    private int putValueA=10;
    private int putValueS=10;
    private int putValueAS=10;
    private int LoaderId=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        mSpinner1= findViewById(R.id.spinner1);
        mSpinner2 = findViewById(R.id.spinner2);
        mEditText = findViewById(R.id.editText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSomething();
            }

        });

    }
    private void doSomething() {
        String table = mSpinner1.getSelectedItem().toString();
        String action= mSpinner2.getSelectedItem().toString();
        EditText name = findViewById(R.id.name);
        String data1 = name.getText().toString();
        name = findViewById(R.id.name2);
        String data2 = name.getText().toString();
        switch (action) {
            case "query":
                url = "content://com.elegion.roomdatabase.musicprovider/"+table.toLowerCase();
                Log.w("table", url);
//                Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
                getSupportLoaderManager().initLoader(LoaderId, null, this);
                LoaderId++;
                return;
            case "update":
                try {
                    int id = Integer.parseInt(mEditText.getText().toString());
                switch (table){
                    case "Album":
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", 0);
                        contentValues.put("name", data1);
                        contentValues.put("release", data2);
                        getContentResolver().update(Uri.parse("content://com.elegion.roomdatabase.musicprovider/album/"+id), contentValues, null, null);
                        return;
                    case "Song":
                            ContentValues contentValues1 = new ContentValues();
                            contentValues1.put("id", 0);
                            contentValues1.put("name", data1);
                            contentValues1.put("duration", data2);
                            getContentResolver().update(Uri.parse("content://com.elegion.roomdatabase.musicprovider/song/"+id), contentValues1, null, null);
                            return;
                    case "AlbumSong":
                        try {
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("id", 0);
                        contentValues2.put("album_id", Integer.parseInt(data1));
                        contentValues2.put("song_id", Integer.parseInt(data2));
                        getContentResolver().update(Uri.parse("content://com.elegion.roomdatabase.musicprovider/albumsong/"+id), contentValues2, null, null);
                        return;}
                        catch (NumberFormatException e) {
                            Toast.makeText(this, "Неправильно заполнены поля", Toast.LENGTH_SHORT).show();
                            return;
                        }

                }
                } catch (Exception e) {
                    Toast.makeText(this, "Ошибка id", Toast.LENGTH_SHORT).show();
                    return;
                }
            case "insert":
                    switch (table) {
                        case "Album":
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("id", putValueA);
                            putValueA++;
                            contentValues.put("name", data1);
                            contentValues.put("release", data2);
                            getContentResolver().insert(Uri.parse("content://com.elegion.roomdatabase.musicprovider/album"), contentValues);
                            return;
                        case "Song":
                            ContentValues contentValues1 = new ContentValues();
                            contentValues1.put("id", putValueS);
                            putValueS++;
                            contentValues1.put("name", data1);
                            contentValues1.put("duration", data2);
                            getContentResolver().insert(Uri.parse("content://com.elegion.roomdatabase.musicprovider/song"), contentValues1);
                            return;
                        case "AlbumSong":
                            try {
                            ContentValues contentValues2 = new ContentValues();
                            contentValues2.put("id", putValueAS);
                            putValueAS++;
                            contentValues2.put("album_id", Integer.parseInt(data1));
                            contentValues2.put("song_id", Integer.parseInt(data2));
                            getContentResolver().insert(Uri.parse("content://com.elegion.roomdatabase.musicprovider/albumsong"), contentValues2);
                            return;}
                            catch (NumberFormatException e)
                            {
                                Toast.makeText(this, "Неправильно заполнены поля", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(this, "Не существует данных", Toast.LENGTH_SHORT).show();
                                return;
                            }
                    }
            case "delete":
                try {
                    int id = Integer.parseInt(mEditText.getText().toString());
                    getContentResolver().delete(Uri.parse("content://com.elegion.roomdatabase.musicprovider/"+table.toLowerCase()+"/"+id), null, null);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Ошибка id", Toast.LENGTH_SHORT).show();
                    return;
                } catch (Exception e) {
                    Toast.makeText(this, "Операция нарушает внешние ссылки, сначала удалите записи из albumsong", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                Uri.parse(url),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            StringBuilder builder = new StringBuilder();
            String[] names = data.getColumnNames();
            int p=0;
            do {
                for (String name : names){
                    builder.append(data.getString(data.getColumnIndex(name))).append(" ");
                    p=data.getInt(data.getColumnIndex("id"));
                }
                builder.append("\n");
            } while (data.moveToNext());
            if(names[2].equals("release")){
                putValueA=p+1;
            } else if(names[2].equals("duration")){
                putValueS=p+1;
            } else {
                putValueAS=p+1;
            }
            Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
        }
        getSupportLoaderManager().destroyLoader(loader.getId());

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
