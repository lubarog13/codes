package com.elegion.recyclertest;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, ContactsAdapter.OnItemClickListener {

    // добавить фрагмент с recyclerView ---
    // добавить адаптер, холдер и генератор заглушечных данных ---
    // добавить обновление данных и состояние ошибки ---
    // добавить загрузку данных с телефонной книги ---
    // добавить обработку нажатий ---
    // добавить декораторы ---
    private String local_id = null;
    private final int LOADER_ID = 12567;
    private LoaderManager loaderManager;
    private Loader<String> loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.loaderManager = getLoaderManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public void onItemClick(String id) {
        this.local_id =id;
        Bundle bundle = new Bundle();
        this.loader = this.loaderManager.initLoader(LOADER_ID, bundle, this);
        this.loader.forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        Log.i("Loader", "Created");
        return new ContactsLoader(MainActivity.this, local_id);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        Log.i("Loader", "Finished");
        this.local_id=null;
        if (s!=null){
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + s)));
        }
        else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        this.loaderManager.destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.stop:
                if(loader.isStarted()) {
                    loaderManager.destroyLoader(loader.getId());
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Звонок отменен");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                    Toast.makeText(this, "Звонок отменен", Toast.LENGTH_SHORT).show();}
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
