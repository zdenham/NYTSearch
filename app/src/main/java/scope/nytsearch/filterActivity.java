package scope.nytsearch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.parceler.Parcels;

import java.util.ArrayList;

public class filterActivity extends AppCompatActivity {

    EditText etMinDate;
    EditText etMaxDate;
    EditText etMinWords;
    EditText etMaxWords;
    CheckBox cbCulture;
    CheckBox cbSports;
    CheckBox cbFashion;
    CheckBox cbForeign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etMinDate = (EditText)findViewById(R.id.etStartDate);
        etMaxDate = (EditText)findViewById(R.id.etEndDate);
        etMinWords = (EditText)findViewById(R.id.etMinWords);
        etMaxWords = (EditText)findViewById(R.id.etMaxWords);
        cbCulture = (CheckBox)findViewById(R.id.cbCulture);
        cbSports = (CheckBox)findViewById(R.id.cbSports);
        cbFashion = (CheckBox)findViewById(R.id.cbFashion);
        cbForeign = (CheckBox)findViewById(R.id.cbForeign);
    }

    public void saveFilter(View v){
        ArrayList<String> topics = new ArrayList<String>();
        if(cbCulture.isChecked()){
            topics.add(cbCulture.getText().toString());
        }
        if(cbSports.isChecked()){
            topics.add(cbSports.getText().toString());
        }
        if(cbFashion.isChecked()){
            topics.add(cbFashion.getText().toString());
        }
        if(cbForeign.isChecked()){
            topics.add(cbForeign.getText().toString());
        }
        Restrictions restrictionSet = new Restrictions( etMinDate.getText().toString(), etMaxDate.getText().toString(), topics, etMinWords.getText().toString(), etMaxWords.getText().toString() );
        Intent data = new Intent();
        Parcelable dat = Parcels.wrap( restrictionSet );
        data.putExtra("data", dat );
        setResult(RESULT_OK, data);
        this.finish();
    }

}
