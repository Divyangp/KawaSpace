package com.example.apicalldemo.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apicalldemo.R;
import com.example.apicalldemo.adapters.UserListAdapter;
import com.example.apicalldemo.models.ResultsEntity;
import com.example.apicalldemo.util.AppGlobal;
import com.example.apicalldemo.util.OnSwipeListener;
import com.example.apicalldemo.viewmodels.UserViewModel;

@SuppressWarnings("deprecation")
public class RandomUserFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rvRandomUser;
    private ImageView ivArrowRight;
    private ImageView ivArrowLeft;
    private ImageView civUser;
    private TextView tvUserName;
    private TextView tvStreetNumber;
    private TextView tvStreetName;
    private TextView tvCountryName;
    private TextView tvTimeZone;
    private TextView tvPostCode;
    private TextView tvDesc;
    private TextView tvGender;
    private UserViewModel viewModel;
    private UserListAdapter adapter = null;
    private ResultsEntity selectedUser = null;
    private ProgressDialog dialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressDialog();
        adapter = new UserListAdapter(requireActivity(), (item, pos) -> {
            adapter.setSelectedItemPos(pos);
            selectedUser = adapter.getItemAtPos(pos);
            updateUserCard();
        });
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.init();
        viewModel.getUserResponseLiveData().observe(this, userResponse -> {
            if (userResponse != null) {
                adapter.setResults(userResponse.results);
                if (!userResponse.results.isEmpty()) {
                    selectedUser = userResponse.results.get(0);
                    updateUserCard();
                }
            }
            hideProgressDialog();
        });
    }

    @SuppressLint("DefaultLocale")
    private void updateUserCard() {
        if (selectedUser != null) {
            Glide.with(requireActivity())
                    .load(selectedUser.picture.large)
                    .optionalFitCenter()
                    .apply(RequestOptions.circleCropTransform())
                    .dontAnimate()
                    .into(civUser);
            tvUserName.setText(String.format("%s %s %s", selectedUser.name.title, selectedUser.name.first, selectedUser.name.last));
            tvStreetNumber.setText(String.format("%d, ", selectedUser.location.street.number));
            tvStreetName.setText(selectedUser.location.street.name);
            tvCountryName.setText(String.format("%s, ", selectedUser.location.country));
            tvTimeZone.setText(String.format("%s - ", selectedUser.location.timezone.offset));
            tvPostCode.setText(selectedUser.location.postcode);
            tvDesc.setText(selectedUser.location.timezone.description);
            tvGender.setText(selectedUser.gender);
            SpannableString content = new SpannableString(selectedUser.name.title + ". " + selectedUser.name.first + " " + selectedUser.name.last);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvUserName.setText(content);
            rvRandomUser.smoothScrollToPosition(adapter.getSelectedItemPos());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_user, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(View view) {
        rvRandomUser = view.findViewById(R.id.rvRandomUser);
        rvRandomUser.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRandomUser.setAdapter(adapter);
        if (AppGlobal.isOnline(requireActivity())) {
            viewModel.getRandomUsers("gender,name,nat,location,picture,email", "20");
        } else {
            hideProgressDialog();
            Toast.makeText(requireActivity(), getResources().getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
        }
        ImageView ivMenu = view.findViewById(R.id.ivMenu);
        civUser = view.findViewById(R.id.civUser);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvStreetNumber = view.findViewById(R.id.tvStreetNumber);
        tvStreetName = view.findViewById(R.id.tvStreetName);
        tvCountryName = view.findViewById(R.id.tvCountryName);
        tvTimeZone = view.findViewById(R.id.tvTimeZone);
        tvPostCode = view.findViewById(R.id.tvPostCode);
        tvDesc = view.findViewById(R.id.tvDesc);
        tvGender = view.findViewById(R.id.tvGender);
        ivArrowRight = view.findViewById(R.id.ivArrowRight);
        ivArrowLeft = view.findViewById(R.id.ivArrowLeft);
        CardView cvSelectedUser = view.findViewById(R.id.cvSelectedUser);
        ivMenu.setOnClickListener(this);
        ivArrowRight.setOnClickListener(this);
        ivArrowLeft.setOnClickListener(this);
        cvSelectedUser.setOnTouchListener(new OnSwipeListener(requireActivity()) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                ivArrowRight.performClick();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                ivArrowLeft.performClick();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMenu:
                menuPopup(v);
                break;
            case R.id.ivArrowLeft:
                if (adapter != null) {
                    int itemCount = (adapter.getItemCount() - 1);
                    if (adapter.getSelectedItemPos() < itemCount) {
                        adapter.setSelectedItemPos((adapter.getSelectedItemPos() + 1));
                        selectedUser = adapter.getSelectedUser();
                        updateUserCard();
                    } else {
                        Toast.makeText(requireActivity(), "Limit Reach", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.ivArrowRight:
                if (adapter != null) {
                    if (adapter.getSelectedItemPos() > 0) {
                        adapter.setSelectedItemPos((adapter.getSelectedItemPos() - 1));
                        selectedUser = adapter.getSelectedUser();
                        updateUserCard();
                    } else {
                        Toast.makeText(requireActivity(), "Limit Reach", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @SuppressLint("RtlHardcoded")
    private void menuPopup(View view) {
        View inflate = View.inflate(requireActivity(), R.layout.popup_menu, (ViewGroup) null);
        PopupWindow popupWindow = new PopupWindow(inflate, -2, -2, true);
        popupWindow.setOutsideTouchable(true);
        LinearLayout llMenuContainer = (LinearLayout) inflate.findViewById(R.id.llMenuContainer);
        llMenuContainer.setOnClickListener(view1 -> popupWindow.dismiss());
        popupWindow.showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 0, 0);
    }

    private void showProgressDialog() {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(requireActivity());
                dialog.setMessage(getResources().getString(R.string.please_wait));
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                if (!requireActivity().isFinishing()) dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideProgressDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}