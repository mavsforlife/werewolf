package com.victor.wolves.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.wolves.Model.DieType;
import com.victor.wolves.Model.Role;
import com.victor.wolves.R;
import com.victor.wolves.adapter.RolAdapter;
import com.victor.wolves.adapter.SetRoleAdapter;
import com.victor.wolves.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetRoleActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String ROLE_LIST = "role_list";
    private static final String ROLE_COUNT = "role_count";
    private static final String IS_NEED_SIGN_LOVER = "isNeedSignLover";

    @BindView(R.id.dl)
    DrawerLayout mDl;
    @BindView(R.id.rv_role)
    RecyclerView mRvRole;
    @BindView(R.id.lv_role_start)
    ListView mLvStart;
    @BindView(R.id.lv_role_end)
    ListView mLvEnd;
    @BindView(R.id.sc_alive_start)
    SwitchCompat mScStart;
    @BindView(R.id.sc_alive_end)
    SwitchCompat mScEnd;
    @BindView(R.id.tv_confirm_start)
    TextView mTvConfirmStart;
    @BindView(R.id.tv_confirm_end)
    TextView mTvConfirmEnd;

    @BindView(R.id.ll_die_type_start)
    GridLayout mLlDieTypeStart;
    @BindView(R.id.tv_poison_start)
    TextView mTvPoisonStart;
    @BindView(R.id.tv_exile_start)
    TextView mTvExileStart;
    @BindView(R.id.tv_kill_start)
    TextView mTvKillStart;
    @BindView(R.id.tv_hunt_start)
    TextView mTvHuntStart;
    @BindView(R.id.tv_for_love_start)
    TextView mTvForLoveStart;
    @BindView(R.id.tv_sign_status_start)
    TextView mtvSignStatusStart;

    @BindView(R.id.ll_die_type_end)
    GridLayout mLlDieTypeEnd;
    @BindView(R.id.tv_poison_end)
    TextView mTvPoisonEnd;
    @BindView(R.id.tv_exile_end)
    TextView mTvExileEnd;
    @BindView(R.id.tv_kill_end)
    TextView mTvKillEnd;
    @BindView(R.id.tv_hunt_end)
    TextView mTvHuntEnd;
    @BindView(R.id.tv_for_love_end)
    TextView mTvForLoveEnd;
    @BindView(R.id.tv_sign_status_end)
    TextView mtvSignStatusEnd;

    @BindView(R.id.rl_sign_lover_start)
    RelativeLayout mRlSignLoverStart;
    @BindView(R.id.sc_sign_lover_start)
    SwitchCompat mScSignLoverStart;
    @BindView(R.id.rl_sign_lover_end)
    RelativeLayout mRlSignLoverEnd;
    @BindView(R.id.sc_sign_lover_end)
    SwitchCompat mScSignLoverEnd;

    private List<Role> mList;
    private RolAdapter mRolAdapter;
    private SetRoleAdapter mAdapter;

    private List<Role> mRoleList;
    private Role mGameRole;
    private int mPlayerCount;
    private int mCurrentPos;
    private int mDieType;
    private boolean mIsNeedSignLover;
    private boolean mIsLover;

    private List<TextView> mTextViewsStart;
    private List<TextView> mTextViewsEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_role);
        ButterKnife.bind(this);

        mRoleList = getIntent().getParcelableArrayListExtra(ROLE_LIST);
        mPlayerCount = getIntent().getIntExtra(ROLE_COUNT, -1);
        mIsNeedSignLover = getIntent().getBooleanExtra(IS_NEED_SIGN_LOVER, false);
        initData();
    }

    private void initData() {

        mGameRole = new Role();
        mGameRole.setName(getString(R.string.Villager));
        mGameRole.setAlive(true);
        mList = new ArrayList<>();
        if (mPlayerCount != -1) {
            for (int i = 0; i < mPlayerCount; i++) {
                Role role = new Role();
                role.setName(getString(R.string.Villager));
                role.setAlive(true);
                mList.add(role);
            }
        }
        mCurrentPos = 0;
        mAdapter = new SetRoleAdapter(this);
        mRvRole.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        mRvRole.setAdapter(mAdapter);
        mAdapter.setData(mList);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onItemClick(int position) {

                mCurrentPos = position;
                mGameRole = mList.get(mCurrentPos);
                mDieType = mGameRole.getDieType();
                mIsLover = mGameRole.isLover();
                if ((mList.size() % 2 == 0 && position < mList.size() / 2) ||
                        (mList.size() % 2 != 0 && position < mList.size() / 2 + 1)) {
                    if (mDl.isDrawerOpen(Gravity.RIGHT)) {
                        mDl.closeDrawer(Gravity.RIGHT);
                    } else {
                        mtvSignStatusEnd.setText(String.format(getString(R.string.sing_player_status), (position + 1)));
                        mRolAdapter.setRole(mGameRole);
                        mScEnd.setChecked(mGameRole.isAlive());
                        mScSignLoverEnd.setChecked(mIsLover);
                        checkDieTypeLlIsVisible(mGameRole.isAlive());
                        for (TextView tv : mTextViewsEnd) {
                            if (((int) tv.getTag()) == mDieType) {
                                tv.setSelected(true);
                            } else {
                                tv.setSelected(false);
                            }
                        }
                        mDl.openDrawer(Gravity.RIGHT);
                    }
                } else {
                    if (mDl.isDrawerOpen(Gravity.LEFT)) {
                        mDl.closeDrawer(Gravity.LEFT);
                    } else {
                        mtvSignStatusStart.setText(String.format(getString(R.string.sing_player_status), (position + 1)));
                        mRolAdapter.setRole(mGameRole);
                        mScStart.setChecked(mGameRole.isAlive());
                        mScSignLoverStart.setChecked(mIsLover);
                        checkDieTypeLlIsVisible(mGameRole.isAlive());
                        for (TextView tv : mTextViewsStart) {
                            if (((int) tv.getTag()) == mDieType) {
                                tv.setSelected(true);
                            } else {
                                tv.setSelected(false);
                            }
                        }
                        mDl.openDrawer(Gravity.LEFT);
                    }
                }
            }
        });

        mRolAdapter = new RolAdapter(this, mRoleList, mGameRole);
        mLvEnd.setAdapter(mRolAdapter);
        mLvEnd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGameRole = mRoleList.get(position);
                mRolAdapter.setRole(mGameRole);
            }
        });

        mLvStart.setAdapter(mRolAdapter);
        mLvStart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGameRole = mRoleList.get(position);
                mRolAdapter.setRole(mGameRole);
            }
        });

        mScStart.setOnCheckedChangeListener(this);
        mScEnd.setOnCheckedChangeListener(this);
        mDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        setSignLoverVisible();
        mScSignLoverStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsLover = isChecked;
            }
        });

        mScSignLoverEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsLover = isChecked;
            }
        });
        mTextViewsEnd = new ArrayList<>();
        mTextViewsStart = new ArrayList<>();

        mTextViewsEnd.add(mTvPoisonEnd);
        mTextViewsEnd.add(mTvExileEnd);
        mTextViewsEnd.add(mTvKillEnd);
        mTextViewsEnd.add(mTvHuntEnd);
        mTextViewsEnd.add(mTvForLoveEnd);

        mTextViewsStart.add(mTvPoisonStart);
        mTextViewsStart.add(mTvExileStart);
        mTextViewsStart.add(mTvKillStart);
        mTextViewsStart.add(mTvHuntStart);
        mTextViewsStart.add(mTvForLoveStart);

        for (int i = 0; i < mTextViewsStart.size(); i++) {
            mTextViewsStart.get(i).setTag((i + 1));
        }

        for (int i = 0; i < mTextViewsEnd.size(); i++) {
            mTextViewsEnd.get(i).setTag((i + 1));
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (mDl.isDrawerOpen(Gravity.LEFT)) {
            mDl.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mDl.isDrawerOpen(Gravity.RIGHT)) {
            mDl.closeDrawer(Gravity.RIGHT);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.message_clear_data)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SetRoleActivity.super.onBackPressed();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("RtlHardcoded")
    @OnClick({R.id.tv_confirm_start, R.id.tv_confirm_end})
    void onBtnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm_start:
                confirmSetRole();
                if (mDl.isDrawerOpen(Gravity.LEFT)) {
                    mDl.closeDrawer(Gravity.LEFT);
                }
                break;

            case R.id.tv_confirm_end:
                confirmSetRole();
                if (mDl.isDrawerOpen(Gravity.RIGHT)) {
                    mDl.closeDrawer(Gravity.RIGHT);
                }
                break;
        }
    }

    private void confirmSetRole() {
        if (!mScEnd.isChecked() && mDieType == 0) {
            Toast.makeText(this, R.string.select_die_type, Toast.LENGTH_LONG).show();
            return;
        }

        mGameRole.setAlive(mScEnd.isChecked());
        mGameRole.setDieTye(mDieType);
        mList.get(mCurrentPos).setName(mGameRole.getName());
        mList.get(mCurrentPos).setAlive(mGameRole.isAlive());
        mList.get(mCurrentPos).setDieTye(mGameRole.getDieType());
        if (mIsNeedSignLover) {
            mList.get(mCurrentPos).setLover(mIsLover);
        }
        mAdapter.notifyDataSetChanged();
    }

    public static void enter(Context context, ArrayList<Role> list, int count, boolean isNeedSignLover) {
        Intent intent = new Intent(context, SetRoleActivity.class);
        intent.putParcelableArrayListExtra(ROLE_LIST, list);
        intent.putExtra(ROLE_COUNT, count);
        intent.putExtra(IS_NEED_SIGN_LOVER, isNeedSignLover);
        context.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkDieTypeLlIsVisible(isChecked);
        if (isChecked) {
            mGameRole.setDieTye(DieType.Null.status());
        }

        for (TextView tv : mTextViewsStart) {
            tv.setSelected(false);
        }
        for (TextView tv : mTextViewsEnd) {
            tv.setSelected(false);
        }
        mDieType = 0;
    }

    @OnClick({R.id.tv_poison_end, R.id.tv_exile_end, R.id.tv_kill_end, R.id.tv_hunt_end, R.id.tv_for_love_end})
    void onSetDieTypeEnd(TextView v) {
        for (TextView tv : mTextViewsEnd) {
            int intTemp1 = (int) v.getTag();
            int intTemp2 = (int) tv.getTag();
            if (intTemp1 == intTemp2) {
                v.setSelected(true);
                mDieType = (int) v.getTag();
            } else {
                tv.setSelected(false);
            }
        }
    }

    @OnClick({R.id.tv_poison_start, R.id.tv_exile_start, R.id.tv_kill_start, R.id.tv_hunt_start, R.id.tv_for_love_start})
    void onSetDieTypeStart(TextView v) {
        for (TextView tv : mTextViewsStart) {
            int intTemp1 = (int) v.getTag();
            int intTemp2 = (int) tv.getTag();
            if (intTemp1 == intTemp2) {
                v.setSelected(true);
                mDieType = (int) v.getTag();
            } else {
                tv.setSelected(false);
            }
        }
    }

    @OnClick({R.id.tv_sign_status_start, R.id.tv_sign_status_end, R.id.tv_select_id_start,
            R.id.tv_select_id_end, R.id.ll_die_type_start, R.id.ll_die_type_end})
    void onReturnNull() {

    }

    private void checkDieTypeLlIsVisible(boolean isChecked) {
        mLlDieTypeEnd.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        mLlDieTypeStart.setVisibility(isChecked ? View.GONE : View.VISIBLE);
    }

    private void setSignLoverVisible() {
        mRlSignLoverStart.setVisibility(mIsNeedSignLover ? View.VISIBLE : View.GONE);
        mRlSignLoverEnd.setVisibility(mIsNeedSignLover ? View.VISIBLE : View.GONE);
    }
}
