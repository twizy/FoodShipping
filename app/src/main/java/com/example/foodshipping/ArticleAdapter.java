package com.example.foodshipping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class ArticleAdapter extends ArrayAdapter<ArticleClass>{
    private Activity context;
    private List<ArticleClass> listObject;
    private FirebaseAuth firebaseAuth;

    public ArticleAdapter(Activity context, List<ArticleClass> listObject) {
        super(context, R.layout.list_layout, listObject);
        this.context = context;
        this.listObject = listObject;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = this.context.getLayoutInflater().inflate(R.layout.list_layout, (ViewGroup) null, true);

        TextView productKey = (TextView) listViewItem.findViewById(R.id.layout_key_id);
        TextView productname = (TextView) listViewItem.findViewById(R.id.layout_product_name);
        TextView productunit = (TextView) listViewItem.findViewById(R.id.layout_product_unit);
        TextView productprice = (TextView) listViewItem.findViewById(R.id.layout_product_price);
        TextView productquantity = (EditText) listViewItem.findViewById(R.id.layout_quantity);

        TextView addstock1 = (TextView) listViewItem.findViewById(R.id.layout_product_stock1);
        TextView addstock2 = (TextView) listViewItem.findViewById(R.id.layout_product_stock2);
        TextView linear = (LinearLayout) listViewItem.findViewById(R.id.quantity_linear);

        ArticleClass articleClass_object = this.listObject.get(position);

        productKey.setText(articleClass_object.getProductKey());
        productname.setText(articleClass_object.getProductname());
        productunit.setText(articleClass_object.getProductunit());
        productprice.setText(articleClass_object.getProductprice());

        firebaseAuth = FirebaseAuth.getInstance();

        addstock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linear.setVisibility(View.VISIBLE);
                addstock1.setVisibility(View.GONE);
                addstock2.setVisibility(View.VISIBLE);
            }
        });

        addstock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productquantity.getText().toString().isEmpty()){
                    Toast.makeText(context, "La quantité est vide !", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("Demo-Minagris").child("Stocks");
                    HashMap<String, Object> newProd = new HashMap<>();
                    newProd.put("productname", productname.getText().toString());
                    newProd.put("productunit", productunit.getText().toString());
                    newProd.put("productprice", productprice.getText().toString());
                    newProd.put("productKey", productKey.getText().toString());
                    newProd.put("productquantity", productquantity.getText().toString());

                    String insKey = databaseReference.push().getKey();

                    newProd.put("stockey", insKey);

                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child(insKey)
                            .updateChildren(newProd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                linear.setVisibility(View.GONE);
                                addstock1.setVisibility(View.VISIBLE);
                                addstock2.setVisibility(View.GONE);
                                productquantity.setText("");
                                Toast.makeText(context, "Succès !", Toast.LENGTH_LONG).show();
//                                Intent intent = new Intent(getContext(), ReducePrice.class);
//                                context.startActivity(intent);
//                                context.finish();
                            }else {
                                Toast.makeText(context, "Erreur !", Toast.LENGTH_LONG).show();
                                System.out.println(task.getException().toString()+ "\n");
                            }
                        }
                    });

                }
            }
        });

        return listViewItem;
    }
}
