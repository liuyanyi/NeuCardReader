package com.wolfaonliu.cardreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wolfaonliu.cardreader.Util.Util;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        AboutFragment aboutFragment = new AboutFragment();
        getFragmentManager().beginTransaction().replace(R.id.aboutlayout, aboutFragment).commit();
        getFragmentManager().executePendingTransactions();
        Preference p = aboutFragment.findPreference("ver");
        try {
            p.setSummary(Util.getVersion(this.getApplicationContext()));
        } catch (NullPointerException e) {

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class AboutFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.info_about);
        }

        private void showEmailDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle(R.string.Report)
                    .setMessage(R.string.report_atten);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"wolfaonliu@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + getString(R.string.report));
                    startActivity(Intent.createChooser(email, getString(R.string.email_clint)));
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.show();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            Uri uri;
            Intent intent;
            switch (preference.getOrder()) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    showEmailDialog();
                    break;
                case 3:
                    uri = Uri.parse("https://github.com/liuyanyi/NewcapecCardReader");
                    intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }
}
