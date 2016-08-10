package scope.nytsearch;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zdenham on 8/8/16.
 */


@Parcel
public class Restrictions {

    public String startDate;
    public String endDate;
    public ArrayList<String> topics;
    public String minLength;
    public String maxLength;

    public Restrictions() {
        startDate = "";
        endDate = "";
        minLength = "";
        maxLength = "";
        topics = new ArrayList<String>();
    }

    public Restrictions(String minDate, String maxDate, ArrayList<String> topics, String minNumb, String maxNumb) {
        startDate = minDate;
        endDate = maxDate;
        this.topics = topics;
        minLength = minNumb;
        maxLength = maxNumb;
    }

    public ArrayList<Boolean> isUsed(){
        ArrayList<Boolean> changes = new ArrayList<Boolean>(Arrays.asList(new Boolean[5]));
        if(startDate != ""){
            changes.set(0, true);
        }
        if(endDate != ""){
            changes.set(1, true);
        }
        if(topics.size() != 0){
            changes.set(2, true);
        }
        if(minLength != ""){
            changes.set(3, true);
        }
        if(maxLength != ""){
            changes.set(4, true);
        }

        return changes;

    }
}
