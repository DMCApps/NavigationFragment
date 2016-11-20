#Navigation Manager Fragment

## Author

Daniel Carmo, dcarmo@alumni.uoguelph.ca

##Adding the library to your android studio project

In your app build.gradle file add the following to your dependencies. Project only available on jCenter repository.

```groovy
compile 'com.dmcapps:navigation-fragment:1.0.0'
```

##Current Version

1.0.0

##Introduction

This project uses the android support libraries for implementation.

The purpose of this manager is to handle a single stack flow of fragments on the screen so that the developer can easily create flows without having to worry about using the FragmentManager and ChildFragmentManager. The single instance of the NavigationManagerFragment will easily handle the presenting and dismissing of Fragments as they are created and added or removed from the stack.

Every Fragment in the Navigation Stack must extend NavigationFragment in order to properly be displayed and navigated. Every NavigationFragment will have access to the NavigationManagerFragment in order to push and pop Fragments from the stack. Further details below will explain how to use the functionality provided by this Manager.

#Implementation

##The Stack Fragment Manager

Use the Stack Fragment Manager just like a normal fragment. Add it to the manager with an initial fragment and you are ready to use the Navigation Manager.

java
```
public class SingleStackNavigationExampleActivity extends AppCompatActivity {

    private static final String STATE_NAV_TAG = "NAV_TAG";

    private String mStackNavigationManagerFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mStackNavigationManagerFragmentTag = savedInstanceState.getString(STATE_NAV_TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mStackNavigationManagerFragmentTag == null) {
            StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance(SampleFragment.newInstance("Root Fragment in the Stack", 0));

            mStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, navManager, mStackNavigationManagerFragmentTag);
            ft.commit();
        }
        else {
            Fragment fragment = getFragmentManager().findFragmentByTag(mStackNavigationManagerFragmentTag);

            if (fragment != null && fragment.isDetached()) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.attach(fragment);
                ft.commit();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(mStackNavigationManagerFragmentTag);
        ft.detach(fragment);
        ft.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_NAV_TAG, mStackNavigationManagerFragmentTag);
    }

    @Override
    public void onBackPressed() {
        NavigationManagerFragment fragment = (NavigationManagerFragment)getFragmentManager().findFragmentByTag(mStackNavigationManagerFragmentTag);
        if (!fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
```

Now from within your Fragments you can easily present and dismiss fragments using. All fragments that you would like to manager using the NavigationManager must extend `NavigationFragment`.

java
```
Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));
presentFragment(fragmentToPresent);
```

Here is an example of the SampleFragment

java
```
public class SampleFragment extends NavigationFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_FRAG_TEXT = "ARG_FRAG_TEXT";
    private static final String ARG_FRAG_COUNT = "ARG_FRAG_COUNT";

    private static final String ARG_MODEL_FROM_NAV_BUNDLE = "ARG_MODEL_FROM_NAV_BUNDLE";

    private String mFragText;
    private SampleModel model;

    private EditText edit1;
    private EditText edit2;
    private EditText edit3;

    private int mFragCount;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SampleFragment.
     */
    public static SampleFragment newInstance(String param1, int fragCount) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRAG_TEXT, param1);
        args.putInt(ARG_FRAG_COUNT, fragCount);
        fragment.setArguments(args);
        return fragment;
    }

    public SampleFragment() {
        // Required empty public constructor
        model = new SampleModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragText = getArguments().getString(ARG_FRAG_TEXT);
            mFragCount = getArguments().getInt(ARG_FRAG_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getNavBundle() != null) {
            model = (SampleModel)getNavBundle().getSerializable(ARG_MODEL_FROM_NAV_BUNDLE);

            if (edit1 != null) {
                edit1.setText(model.text1);
                edit2.setText(model.text2);
                edit3.setText(model.text3);
            }
        }

        setTitle("Sample Fragment " + mFragCount);

        if (getNavigationManager() instanceof MasterDetailNavigationManagerFragment) {
            setMasterToggleTitle("Master");
        }

        // Using this to test if the memory space of the activity changes on rotation in the child
        Activity activity = getActivity();
        if (activity != null) {
            // Debug into this to check mHost is changed.
            setHasOptionsMenu(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        ((TextView) view.findViewById(R.id.sample_tv_text)).setText((mFragCount + 1) + " " + mFragText);

        edit1 = (EditText) view.findViewById(R.id.sample_et_text_1);
        edit1.setText(model.text1);
        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.text1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit2 = (EditText) view.findViewById(R.id.sample_et_text_2);
        edit2.setText(model.text2);
        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.text2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit3 = (EditText) view.findViewById(R.id.sample_et_text_3);
        edit3.setText(model.text3);
        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.text3 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        view.findViewById(R.id.sample_btn_present).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));
                presentFragment(fragmentToPresent);
            }
        });

        view.findViewById(R.id.sample_btn_present_override_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));

                overrideNextAnimation(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
                presentFragment(fragmentToPresent);
            }
        });

        view.findViewById(R.id.sample_btn_present_bundle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARG_MODEL_FROM_NAV_BUNDLE, new SampleModel(model));
                presentFragment(fragmentToPresent, bundle);
            }
        });

        view.findViewById(R.id.sample_btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFragment();
            }
        });

        view.findViewById(R.id.sample_btn_dismiss_override_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overrideNextAnimation(R.anim.slide_out_to_bottom, R.anim.slide_in_from_top);
                dismissFragment();
            }
        });

        view.findViewById(R.id.sample_btn_dismiss_bundle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARG_MODEL_FROM_NAV_BUNDLE, new SampleModel(model));
                dismissFragment(bundle);
            }
        });

        view.findViewById(R.id.sample_btn_launch_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), TestIntentLaunchingActivity.class));
            }
        });

        view.findViewById(R.id.sample_btn_replace_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation fragmentAsNewRoot = SampleFragment.newInstance("This is a replaced root Fragment", 0);
                replaceRootFragment(fragmentAsNewRoot);
            }
        });

        return view;
    }

    public int getFragCount() {
        return mFragCount;
    }

    private class SampleModel implements Serializable {
        public String text1;
        public String text2;
        public String text3;

        public SampleModel() {

        }

        public SampleModel(SampleModel model) {
            text1 = model.text1;
            text2 = model.text2;
            text3 = model.text3;
        }
    }
}
```

##Change Log

###1.0.0
- Added in non support fragment manager [Git issue 1](https://github.com/DMCApps/NavigationFragment/issues/1)
- Updated package names
- Added in interfaces for all the micromanagers
- Removed the RetainedChildFragmentManager requirements as the newest version of the support library fixes this

###0.3.1
- Marked the `INavigationManager` properties in the ManagerConfig as Transient per [Git issue 26](https://github.com/DMCApps/NavigationFragment/issues/26)

###0.3.0
- Remove Serializable requirement from all classes. There is no need for it anymore and the Navigation Fragment shouldn't make that decision.
- Updated the method for animations. Depreciated helper methods for `present`/`dismiss` that take in animIn and animOut values. Favoring setting the animation using `overrideNextAnimation(int, int)` much like the fragment manager does it. This is so that we can keep the method signature for preset/dismiss down now that we are adding in the bundle as well.
- Fixed [Git issue 6](https://github.com/DMCApps/NavigationFragment/issues/6). You can now present and dismiss with a bundle attached using `presentFragment(INavigationFragment fragment, Bundle bundle);` `and dimissFragment(Bundle bundle);`. Bundle is retreived in the Dismissed/Presented Fragment using `Bundle bundle = getNavBundle();`

NOTE: The present and dismiss share the same bundle and hence setting a bundle on present/dismiss will override the current nav bundle for the specific fragment that is presented or the fragment that is returned to on dismiss.

See [CHANGELOG](https://github.com/DMCApps/NavigationFragment/blob/develop/ChangeLog.md) for past implementation notes and current in progress items.

In Android Studio Terminal use:
```
./gradlew install

./gradlew bintrayUpload
```

#License

Copyright (c) 2016 DMCApps [MIT License](https://opensource.org/licenses/MIT)
