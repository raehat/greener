package com.example.greener;

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

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    private CardStackLayoutManager manager;
    private static final String TAG = "HomeFragment";
    private myadapter adapter;
    ArrayList<DataModel> dataholder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=view.findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder=new ArrayList<>();

        manager = new CardStackLayoutManager(getActivity(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    Toast.makeText(getActivity(), "Direction Right", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Top){
                    Toast.makeText(getActivity(), "Direction Top", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Left){
                    Toast.makeText(getActivity(), "Direction Left", Toast.LENGTH_SHORT).show();
                }
                if (direction == Direction.Bottom){
                    Toast.makeText(getActivity(), "Direction Bottom", Toast.LENGTH_SHORT).show();
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
        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
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
        adapter = new myadapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
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

    private List<DataModel> addList() {
        List<DataModel> items = new ArrayList<>();
        DataModel ob1=new DataModel("A","Angular","Web Application");
        items.add(ob1);

        DataModel ob2=new DataModel("B","C Programming","Embed Programming");
        items.add(ob2);

        DataModel ob3=new DataModel("C","C++ Programming","Embed Programming");
        items.add(ob3);

        DataModel ob4=new DataModel("D",".NET Programming","Desktop and Web Programming");
        items.add(ob4);
        return items;
    }
    private void paginate() {
        //List<DataModel> old = adapter.getItems();
        List<DataModel> baru = new ArrayList<>(addList());
        //CardStackCallback callback = new CardStackCallback(old, baru);
        //DiffUtil.DiffResult hasil = DiffUtil.calculateDiff(callback);
        //adapter.setItems(baru);
        //hasil.dispatchUpdatesTo(adapter);
    }
}
