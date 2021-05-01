package com.example.greener;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseFirestore firestore;
    private CardStackLayoutManager manager;
    private static final String TAG = "HomeFragment";
    CardStackAdapter adapter;
    CardStackView cardStackView;
    List<ItemModel> dataholder;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);

        firestore= FirebaseFirestore.getInstance();
        dataholder=new ArrayList<>();
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setMessage("loading...");
        progressDialog.show();

        firestore.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot querySnapshot: task.getResult())
                {
                    ItemModel model= new ItemModel(querySnapshot.getString("eventName"),querySnapshot.getString("description")
                    ,querySnapshot.getString("imageCode"), querySnapshot.getString("location"));
                    dataholder.add(model);
                }
                adapter = new CardStackAdapter(getContext(), dataholder);
                cardStackView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });

        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }



            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top){
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom){
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {

            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });


        /*List<ItemModel> items = new ArrayList<>();
        ItemModel ob1=new ItemModel("A","Angular","Web Application");
        items.add(ob1);*/

        cardStackView = view.findViewById(R.id.card_stack_view);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());

        cardStackView.setLayoutManager(manager);
        /*adapter=new CardStackAdapter(items);*/

        cardStackView.setItemAnimator(new DefaultItemAnimator());

        /*DataModel ob1=new DataModel(R.drawable.ic_baseline_person_24,"Angular","Web Application");
        dataholder.add(ob1);

        DataModel ob2=new DataModel(R.drawable.ic_baseline_person_24,"C Programming","Embed Programming");
        dataholder.add(ob2);

        DataModel ob3=new DataModel(R.drawable.ic_baseline_person_24,"C++ Programming","Embed Programming");
        dataholder.add(ob3);

        DataModel ob4=new DataModel(R.drawable.ic_baseline_person_24,".NET Programming","Desktop and Web Programming");
        dataholder.add(ob4);

        DataModel ob5=new DataModel(R.drawable.ic_baseline_person_24,"Java Programming","Desktop and Web Programming");
        dataholder.add(ob5);

        recyclerView.setAdapter(new myadapter(dataholder));*/
        return view;
    }

    private List<ItemModel> addList() {
        List<ItemModel> items = new ArrayList<>();
        //ItemModel ob1=new ItemModel("A","Angular","Web Application");
        //items.add(ob1);


        return items;
    }
    private void paginate() {
        List<ItemModel> old = adapter.getItems();
        List<ItemModel> baru = new ArrayList<>(dataholder);
        CardStackCallback callback = new CardStackCallback(old, baru);
        DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        adapter.setItems(baru);
        hasil.dispatchUpdatesTo(adapter);
    }

}
