#Navigation Manager Fragment

## Author

Daniel Carmo, dcarmo@alumni.uoguelph.ca

##Current Version Information

0.0.1 
This project uses the android support libraries for implementation.

##Introduction

The purpose of this manager is to handle a single stack flow of fragments on the screen so that the developer can easily create flows without having to worry about using the FragmentManager and ChildFragmentManager. The single instance of the NavigationManagerFragment will easily handle the presenting and dismissing of Fragments as they are created and added or removed from the stack.

Every Fragment in the Navigation Stack must extend NavigationFragment in order to properly be displayed and navigated. Every NavigationFragment will have access to the NavigationManagerFragment in order to push and pop Fragments from the stack. Further details below will explain how to use the functionality provided by this Manager.

#How to use it

##The Single Stack Fragment Manager

This manager is used to manage a single flow of Fragments in a stack. The stack presents and dismisses in a linear flow from start to end.

##Creating and Displaying the Manager

In order to create an instance of the SingleStackNavigationManagerFragment, the root fragment must be created along side this and given to the manager for use. We do this with the use of the following:

```
NavigationFragment rootFragment = SampleFragment.newInstance("This is the root fragment");
SingleStackNavigationManagerFragment navManager = SingleStackNavigationManagerFragment.newInstance(rootFragment);
```

In the example above, we are creating the rootFragment as the first fragment that we would like to see on the stack. We have now created our SingleStackNavigationManagerFragment and it is ready for use in managing a stack of fragment. Next we will need to present it to the screen as we would any other fragment.

```
mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
ft.add(android.R.id.content, fragment, mSingleStackNavigationManagerFragmentTag);
ft.commit();
```

In the above we are adding the SingleStackNavigationManagerFragment to the screen using the SupportFragmentManager. We are also holding an instance of the tag (mSingleStackNavigationManagerFragmentTag) used incase we need to retrieve this fragment from the manager at a later time.

Now that the SingleStackNavigationManagerFragment has been added to the screen, we should see the contents of the SampleFragment shown on the screen. From here we can go through screen rotations and see that the fragment will be retained and the stack untouched. 

##Presenting a Fragment

Now that the SingleStackNavigationManagerFragment is being put to work, we need to make sure that we are presenting and dismissing fragments from the appropriate methods. Since all fragment in the stack must extend NavigationFragment, we will have access to the appropriate methods for doing so. In order to present a new fragment we have two options. We can use the default animations (slide in right and slide out left).

```
// Inside the Sample Fragment we use the presentFragmnet(NavigationFragment fragment) method.
((Button)view.findViewById(R.id.sample_btn_continue)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SampleFragment.this.presentFragment(SampleFragment.newInstance(++fragCount + " Fragment In The Stack."));
    }
});
```

OR if you would like to provide your own animations you can use the same as the above but use the method with the following signiture

```
presentFragment(NavigationFragment fragment, int animationIn, int animationOut);
```

##Dismissing a Fragment

In order to remove fragments from the screen we must follow a similar style as presenting. To dismiss the fragments we call the dismissFragment() on the NavigationFragment that we are in. We can use the default animations (slide out right and slide in left).

```
// Inside the Sample Fragment we use the dismissFragment() method.
((Button)view.findViewById(R.id.sample_btn_back)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SampleFragment.this.dismissFragment();
    }
});
```

OR if you would like to provide your own animations your can use the same as the above but use the method with the following signiture.

``
dismissFragment(int animationIn, int animationOut);
``

##Keeping the Back Button Functional

In order to use the back button for dismissing the fragments, we will need to add in some custom code to allow the SingleStackNavigationManagerFragment to perform its own back action. To do so we will need to get a hold of the SingleStackNavigationManagerFragment in the onBackPressed and then execute the back action of the Fragment. To do so we use the saved String tag for the SingleStackNavigationManagerFragment and retrieve it from the FragmentManager. 

```
// Inside the Main Activity we use the onBackPressed() method.
@Override
public void onBackPressed() {
    SingleStackNavigationManagerFragment fragment = (SingleStackNavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
    if (!fragment.onBackPressed()) {
        super.onBackPressed();
    }
}
```

We can see here that if the onBackPressed() of the SingleStackNavigationManagerFragment returns false then there are no more fragments in the stack to go back from and hence we perform the default behaviour of the Activity.

##Future Plans and Examples

1. Master-Detail
2. Make into Maven project
3. Tabs
