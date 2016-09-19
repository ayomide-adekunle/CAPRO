package org.heywhyconcept.myapplicationscroll;




import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ShareActionProvider;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private PendingIntent pendingIntent;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("CAPRO International Prayer Focus");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);


        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);






        DateFormat df = new SimpleDateFormat("MMM d");
        String now = df.format(new Date());
        List<String> date=new ArrayList<>();

        DataBaseAccess databaseAccess = DataBaseAccess.getInstance(this);
        databaseAccess.open();
        date=databaseAccess.getDate();
        int count=0;
        for (int i=0;i<=date.size();i++)
        {
            if (now.equals(date.get(i)) || date.get(i)==date.get(date.size()-1)) {

                break;
            }
            count++;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(count);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pray without ceasing (I Thess. 5:17)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            String message = "https://play.google.com/store/apps/details?id=org.heywhyconcept.myapplicationscroll";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "You can download the CAPRO Int'l Prayer focus here:   "+message);
            startActivity(Intent.createChooser(share, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }





    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String Title_Key = "Title_Key";
        private static final String Name_Key = "Name_Key";
        private static final String Date_Key= "Date_Key";


        String linkText = "<a href='http://capromissions.org/'>Contact Us</a>";



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            Bundle bundle=getArguments();

            DateFormat df = new SimpleDateFormat("MMM d");
            String now = df.format(new Date());
            List<String> date=new ArrayList<>();

            DataBaseAccess databaseAccess = DataBaseAccess.getInstance(null);
            databaseAccess.open();
            date=databaseAccess.getDate();



            if (bundle!=null)
            {
                String name=bundle.getString(Name_Key);
                String title=bundle.getString(Title_Key);
                TextView contactUs=(TextView) rootView.findViewById(R.id.footer);
                TextView textView = (TextView) rootView.findViewById(R.id.name);
                TextView title_view = (TextView) rootView.findViewById(R.id.title);
                contactUs.setText(Html.fromHtml(linkText));
                contactUs.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(name);
                title_view.setText(title);
                //setValues(rootView,name);

            }


            return rootView;


        }
        private void setValues( View v, String name, String title)
        {
            TextView textview=(TextView) v.findViewById(R.id.name);
            TextView title_view = (TextView) v.findViewById(R.id.title);
            textview.setText(name);
            title_view.setText(title);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<String> name=new ArrayList<>();
        List<String> title=new ArrayList<>();
        List<String> date=new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            DataBaseAccess databaseAccess = DataBaseAccess.getInstance(null);
            databaseAccess.open();
            name=databaseAccess.getQuotes();
            title=databaseAccess.getTitles();
            date=databaseAccess.getDate();

        }

        @Override
        public Fragment getItem(int position) {

            DateFormat df = new SimpleDateFormat("MMM d");
            String now = df.format(new Date());

            Bundle bundle=new Bundle();
            bundle.putString(PlaceholderFragment.Name_Key, name.get(position));

            if (now.equals(date.get(position)))
            {
                //TextView title_view = (TextView) findViewById(R.id.title);
                bundle.putString(PlaceholderFragment.Title_Key, "Today" + " " + title.get(position));
                //title_view.setBackgroundColor(getResources().getColor(R.color.textcolor));
            }
            else
                {bundle.putString(PlaceholderFragment.Title_Key, title.get(position));}

            PlaceholderFragment deviceFragment= new PlaceholderFragment();
            deviceFragment.setArguments(bundle);
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return deviceFragment;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return name.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name.get(position);
        }
    }
}
