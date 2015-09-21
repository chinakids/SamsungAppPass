package chinakids.applock.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import chinakids.applock.R;
import chinakids.applock.fragments.ColorFragment;
import chinakids.applock.fragments.NavigationDrawerFragment;

/**
 * Main class hosting the navigation drawer
 * <p/>
 * Created by fyunli on 15/4/28.
 */
public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ListView lv;
    private static final String[] strs = new String[] {
        "first", "second", "third", "fourth", "fifth"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(chinakids.applock.R.layout.activity_main);


        initialise();


    }

    /**
     * Bind, create and set up the resources
     */
    private void initialise() {
        // Toolbar
        mToolbar = (Toolbar) findViewById(chinakids.applock.R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.main_activity_navigation_drawer_rootLayout,
                (DrawerLayout) findViewById(chinakids.applock.R.id.main_activity_DrawerLayout), mToolbar);

        // Create the first fragment to be shown
        Bundle bundle = new Bundle();
        bundle.putInt(ColorFragment.sARGUMENT_COLOR, chinakids.applock.R.color.blue_500);

        lv = (ListView) findViewById(R.id.lv);//得到ListView对象的引用 /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));


        getSupportFragmentManager()
                .beginTransaction()
                .add(chinakids.applock.R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                .commit();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mDrawerFragment.setup(R.id.main_activity_navigation_drawer_rootLayout, (DrawerLayout) findViewById(
//                R.id.main_activity_DrawerLayout), mToolbar);
//    }

}
