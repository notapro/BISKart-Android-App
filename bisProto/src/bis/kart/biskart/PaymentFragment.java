package bis.kart.biskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class PaymentFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        lv = (ListView) rootView.findViewById(R.id.ListViewPaymentOp);
        lv.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (i == 0 || i == 1)
        {
            intent = getActivity().getIntent();
            String str = "";
            str += intent.getStringExtra("name") + "\n";
            str += intent.getStringExtra("address") + "\n";
            str += intent.getStringExtra("email") + "\n";
            str += intent.getStringExtra("contact") + "\n";

            intent = new Intent(getActivity(), CardDetails.class);
            intent.putExtra("details", str);
            intent.putExtra("amount", SummaryFragment.total);

            startActivity(intent);
        }
        else if(i==2)
        {

            intent = new Intent(getActivity(), Netbanking.class);
            startActivity(intent);
        }
        else
        {
            intent = getActivity().getIntent();
            String str = "";
            str += intent.getStringExtra("name") + "\n";
            str += intent.getStringExtra("address") + "\n";
            str += intent.getStringExtra("email") + "\n";
            str += intent.getStringExtra("contact") + "\n";
            intent = new Intent(getActivity(), CashOnDelivery.class);
            intent.putExtra("details", str);
            startActivity(intent);
        }
    }
}