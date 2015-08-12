package bis.kart.biskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DeliveryDetailsFragment extends Fragment {

    Intent intent, details;
    TextView name, address, email, contact;
    Button changeAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_delivery_details, container, false);

        name = (TextView) rootView.findViewById(R.id.textDeliveryName);
        address = (TextView) rootView.findViewById(R.id.textDeliveryAddress);
        email = (TextView) rootView.findViewById(R.id.textDeliveryEmail);
        contact = (TextView) rootView.findViewById(R.id.textDeliveryContact);
        changeAddress = (Button) rootView.findViewById(R.id.buttonChangeAddress);

        details = getActivity().getIntent();

        name.setText("\n" + details.getStringExtra("name"));
        address.setText(details.getStringExtra("address"));
        email.setText(details.getStringExtra("email"));
        contact.setText(details.getStringExtra("contact") + "\n");

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), DeliveryDetailsMain.class);

                startActivity(intent);
            }
        });

        return rootView;
    }


}