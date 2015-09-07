package chinakids.applock.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import chinakids.applock.R;
import chinakids.applock.activities.OtherActivity;
import chinakids.applock.customViews.ScrimInsetsFrameLayout;
import chinakids.applock.utils.AndroidUtils;

/**
 * Created by fyunli on 15/4/28.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {

    private final static double sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO = 9d / 16d;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout_AccountView;
    private LinearLayout mNavDrawerEntriesRootView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private FrameLayout mFrameLayout_Home, mFrameLayout_Explore, mFrameLayout_HelpAndFeedback,
            mFrameLayout_About;
    private TextView mTextView_AccountDisplayName, mTextView_AccountEmail;
    private TextView mTextView_Home, mTextView_Explore, mTextView_HelpAndFeedback, mTextView_About;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer, container, false);
        // Layout resources
        mFrameLayout_AccountView = (FrameLayout) view.findViewById(R.id.navigation_drawer_account_view);
        mNavDrawerEntriesRootView = (LinearLayout)
                view.findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Home = (FrameLayout) view.findViewById(R.id.navigation_drawer_items_list_linearLayout_home);
        //mFrameLayout_Explore = (FrameLayout) view.findViewById(R.id.navigation_drawer_items_list_linearLayout_explore);
        mFrameLayout_HelpAndFeedback = (FrameLayout)
                view.findViewById(R.id.navigation_drawer_items_list_linearLayout_help_and_feedback);
        mFrameLayout_About = (FrameLayout) view.findViewById(R.id.navigation_drawer_items_list_linearLayout_about);

        mTextView_AccountDisplayName = (TextView)
                view.findViewById(R.id.navigation_drawer_account_information_display_name);
        mTextView_AccountEmail = (TextView) view.findViewById(R.id.navigation_drawer_account_information_email);

        mTextView_Home = (TextView) view.findViewById(R.id.navigation_drawer_items_textView_home);
        //mTextView_Explore = (TextView) view.findViewById(R.id.navigation_drawer_items_textView_explore);
        mTextView_HelpAndFeedback = (TextView)
                view.findViewById(R.id.navigation_drawer_items_textView_help_and_feedback);
        mTextView_About = (TextView) view.findViewById(R.id.navigation_drawer_items_textView_about);

        // Typefaces
        mTextView_AccountDisplayName.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_medium));
        mTextView_AccountEmail.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_regular));
        mTextView_Home.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_medium));
        //mTextView_Explore.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_medium));
        mTextView_HelpAndFeedback.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_medium));
        mTextView_About.setTypeface(AndroidUtils.getTypeface(this.getActivity(), R.string.typeface_roboto_medium));

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Home.setOnClickListener(this);
        //mFrameLayout_Explore.setOnClickListener(this);
        mFrameLayout_HelpAndFeedback.setOnClickListener(this);
        mFrameLayout_About.setOnClickListener(this);

        return view;
    }

    public void setup(int fragmentContainerId, DrawerLayout drawerLayout, Toolbar toolbar) {
        // Navigation Drawer
        mDrawerLayout = drawerLayout;
        mToolbar = toolbar;

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout)this.getActivity().findViewById(fragmentContainerId);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this.getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_opened,
                R.string.navigation_drawer_closed
        ) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                syncState();
            }

        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = AndroidUtils.getScreenWidth(this.getActivity()) -
                AndroidUtils.getThemeAttributeDimensionSize(this.getActivity(),
                        android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);
        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNAVIGATION_DRAWER_ACCOUNT_SECTION_ASPECT_RATIO);

        // Set the first item as selected for the first time
        mToolbar.setTitle(R.string.toolbar_title_home);
        mFrameLayout_Home.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.navigation_drawer_account_view) {
            mDrawerLayout.closeDrawer(Gravity.START);

            // If the user is signed in, go to the profile, otherwise show sign up / sign in
        } else {
            if (!view.isSelected()) {
                onRowPressed((FrameLayout) view);

                switch (view.getId()) {
                    case R.id.navigation_drawer_items_list_linearLayout_home: {
                        mToolbar.setTitle(getString(R.string
                                .toolbar_title_home));

                        view.setSelected(true);

                        Bundle bundle = new Bundle();
                        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.blue_500);

                        // Insert the fragment by replacing any existing fragment
                        this.getFragmentManager().beginTransaction()
                                .replace(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                                .commit();
                        break;
                    }

//                    case R.id.navigation_drawer_items_list_linearLayout_explore: {
//                        mToolbar.setTitle(getString(R.string.toolbar_title_explore));
//
//                        view.setSelected(true);
//
//                        Bundle bundle = new Bundle();
//                        bundle.putInt(ColorFragment.sARGUMENT_COLOR, R.color.amber_500);
//
//                        this.getFragmentManager().beginTransaction()
//                                .replace(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
//                                .commit();
//                        break;
//                    }

                    case R.id.navigation_drawer_items_list_linearLayout_help_and_feedback:
                        // Start intent to send an email
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    case R.id.navigation_drawer_items_list_linearLayout_about:
                        // Show about activity
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    default:
                        break;
                }
            } else {
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        }
    }

    /**
     * Set up the rows when any is pressed
     *
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(FrameLayout pressedRow) {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry)) {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++) {
                View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry) {
                    if (currentView == pressedRow) {
                        currentView.setSelected(true);
                    } else {
                        currentView.setSelected(false);
                    }
                }
            }
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

}
