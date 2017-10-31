package company.com.xproject;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ManageActivity extends AppCompatActivity {
    public static String TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_manage);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        int type = getIntent().getIntExtra(TYPE, 0);
        Fragment fragment = null;
        switch(type){
            case 0:
                fragment = new LonLatFragment();
                break;
            case 1:
                fragment = new SenderFragment();
                break;
            case 2:
                fragment = new CashFragment();
                break;
            case 3:
                fragment = new ShareFragment();
                break;
        }
        getFragmentManager().beginTransaction().add(R.id.contentlayout, fragment).commit();
    }

}
