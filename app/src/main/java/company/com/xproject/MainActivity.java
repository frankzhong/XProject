package company.com.xproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.button)
    Button lonLatMapView;
    @BindView(R.id.button2)
    Button traceMapView;
    @BindView(R.id.button3)
    Button cashView;
    @BindView(R.id.button4)
    Button shareView;
    android.app.FragmentManager fm;
    android.app.FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    public void click(Button button){
        switch(button.getId()){
            case R.id.button:
                Toast.makeText(this, "根据地址获取经纬度", Toast.LENGTH_SHORT).show();
                selectFragment(0);
                break;
            case R.id.button2:
                Toast.makeText(this, "配送地图路径", Toast.LENGTH_SHORT).show();
                selectFragment(1);
                break;
            case R.id.button3:
                Toast.makeText(this, "银行卡提现", Toast.LENGTH_SHORT).show();
                selectFragment(2);
                break;
            case R.id.button4:
                Toast.makeText(this, "二维码分享", Toast.LENGTH_SHORT).show();
                selectFragment(3);
                break;
            default:
                break;
        }
    }

    private void selectFragment(int type){
        switch(type){
            case 0:
                Intent i1 = new Intent(MainActivity.this, ManageActivity.class);
                i1.putExtra(ManageActivity.TYPE, 0);
                startActivity(i1);
                break;
            case 1:
                Intent i2 = new Intent(MainActivity.this, ManageActivity.class);
                i2.putExtra(ManageActivity.TYPE, 1);
                startActivity(i2);
                break;
            case 2:
                Intent i3 = new Intent(MainActivity.this, ManageActivity.class);
                i3.putExtra(ManageActivity.TYPE, 2);
                startActivity(i3);
                break;
            case 3:
                Intent i4 = new Intent(MainActivity.this, ManageActivity.class);
                i4.putExtra(ManageActivity.TYPE, 3);
                startActivity(i4);
                break;
            default:
                break;
        }

    }
}
