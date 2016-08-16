#Navigation Manager Fragment

## Author

Daniel Carmo, dcarmo@alumni.uoguelph.ca

##Adding the library to your android studio project

In your app build.gradle file add the following to your dependencies. Project only available on jCenter repository.

```groovy
compile 'com.dmcapps:navigation-fragment:0.3.0'
```

##Current Version

0.3.1

##Introduction

This project uses the android support libraries for implementation.

The purpose of this manager is to handle a single stack flow of fragments on the screen so that the developer can easily create flows without having to worry about using the FragmentManager and ChildFragmentManager. The single instance of the NavigationManagerFragment will easily handle the presenting and dismissing of Fragments as they are created and added or removed from the stack.

Every Fragment in the Navigation Stack must extend NavigationFragment in order to properly be displayed and navigated. Every NavigationFragment will have access to the NavigationManagerFragment in order to push and pop Fragments from the stack. Further details below will explain how to use the functionality provided by this Manager.

#Implementation

##The Single Stack Fragment Manager

This manager is used to manage a single flow of Fragments in a stack. The stack presents and dismisses in a linear flow from start to end.

###Creating and Displaying the Manager

In order to create an instance of the SingleStackNavigationManagerFragment, the root fragment must be created along side this and given to the manager for use. We do this with the use of the following:

```java
NavigationFragment rootFragment = SampleFragment.newInstance("This is the root fragment");
SingleStackNavigationManagerFragment navManager = SingleStackNavigationManagerFragment.newInstance(rootFragment);
```

In the example above, we are creating the rootFragment as the first fragment that we would like to see on the stack. We have now created our SingleStackNavigationManagerFragment and it is ready for use in managing a stack of fragment. Next we will need to present it to the screen as we would any other fragment.

```java
mSingleStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
ft.add(android.R.id.content, fragment, mSingleStackNavigationManagerFragmentTag);
ft.commit();
```

In the above we are adding the SingleStackNavigationManagerFragment to the screen using the SupportFragmentManager. We are also holding an instance of the tag (mSingleStackNavigationManagerFragmentTag) used incase we need to retrieve this fragment from the manager at a later time.

Now that the SingleStackNavigationManagerFragment has been added to the screen, we should see the contents of the SampleFragment shown on the screen. From here we can go through screen rotations and see that the fragment will be retained and the stack untouched. 

###Presenting a Fragment

Now that the SingleStackNavigationManagerFragment is being put to work, we need to make sure that we are presenting and dismissing fragments from the appropriate methods. Since all fragment in the stack must extend NavigationFragment, we will have access to the appropriate methods for doing so. In order to present a new fragment we have two options. We can use the default animations (slide in right and slide out left).

```java
// Inside the Sample Fragment we use the presentFragmnet(NavigationFragment fragment) method.
((Button)view.findViewById(R.id.sample_btn_continue)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        INavigationFragment fragmentToPresent = SampleFragment.newInstance(++fragCount + " Fragment In The Stack.");
        presentFragment(fragmentToPresent);
    }
});
```

OR if you would like to provide your own animations you can override the next transition by calling the below method before calling `presentFragment`.

```java
@Depreciated
// DEPRECIATED in 0.3.0 to be removed in 0.4.0
// USE method below.
presentFragment(INavigationFragment fragment, int animIn, int animOut);

overrideNextAnimation(int animIn, int animOut);
INavigationFragment fragmentToPresent = SampleFragment.newInstance(++fragCount + " Fragment In The Stack.");
presentFragment(SampleFragment.newInstance(++fragCount + " Fragment In The Stack."));
```

###Dismissing a Fragment

In order to remove fragments from the screen we must follow a similar style as presenting. To dismiss the fragments we call the dismissFragment() on the NavigationFragment that we are in. We can use the default animations (slide out right and slide in left).

```java
// Inside the Sample Fragment we use the dismissFragment() method.
((Button)view.findViewById(R.id.sample_btn_back)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismissFragment();
    }
});
```

OR if you would like to provide your own animations you can override the next transition by calling the below method before calling `dismissFragment`.

```java
@Depreciated
// DEPRECIATED in 0.3.0 to be removed in 0.4.0
// USE method below.
dismissFragment(int animIn, int animOut);

overrideNextAnimation(int animIn, int animOut);
dismissFragment();
```

###Setting the page title

To set the page title on any of the NavigationManagerFragment just call either of the methods for:

```java
setTitle(int resId);
setTitle(String title);
```

This will update the title for the given page.

###Keeping the Back Button Functional

In order to use the back button for dismissing the fragments, we will need to add in some custom code to allow the SingleStackNavigationManagerFragment to perform its own back action. To do so we will need to get a hold of the SingleStackNavigationManagerFragment in the onBackPressed and then execute the back action of the Fragment. To do so we use the saved String tag for the SingleStackNavigationManagerFragment and retrieve it from the FragmentManager. 

```java
// Inside the Main Activity we use the onBackPressed() method.
@Override
public void onBackPressed() {
    NavigationManagerFragment fragment = (NavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mSingleStackNavigationManagerFragmentTag);
    if (!fragment.onBackPressed()) {
        super.onBackPressed();
    }
}
```

We can see here that if the onBackPressed() of the SingleStackNavigationManagerFragment returns false then there are no more fragments in the stack to go back from and hence we perform the default behaviour of the Activity.

##The Master Detail Fragment Manager

This fragment manager is used to manage the MasterDetail flow of Android. The Master Detail Fragment Manager can handle a single stack of Fragments for a Phone or a split view screen on a Tablet. The current implementation handles only a static master panel with the detail panel being changable and navigatable in a linear fashion.

###Creating and Displaying the Manager

In order to create an instance of the MasterDetailNavigationManagerFragment, the master and detail fragment must be created along side this and given to the manager for use. We do this with the use of the following:

```java
MasterFragment masterFrag = MasterFragment.newInstance();
SampleFragment detailFrag = SampleFragment.newInstance("Detail Fragment in the Stack", 0);
MasterDetailNavigationManagerFragment manager = MasterDetailNavigationManagerFragment.newInstance(masterFrag, detailFrag);
```

In the example above, we are creating the masterFragment as the first fragment that we would like to see on the stack for the phone, and the left panel on the tablet. We then create the first fragment in the detail stack as the second parameter to newInstance. NOTE: On the phone, only the master fragment will be used. The detail fragment will be ignored hence starting the single stack at the detail fragment.

We have now created our MasterDetailNavigationManagerFragment and it is ready for use in managing a stack of fragment. Next we will need to present it to the screen in our activity as we would any other fragment.

```java
mNavigationManagerFragmentTag = UUID.randomUUID().toString();

FragmentManager fm = getSupportFragmentManager();
FragmentTransaction ft = fm.beginTransaction();
ft.add(android.R.id.content, fragment, mNavigationManagerFragmentTag);
ft.commit();
fm.executePendingTransactions();
```

Now we have displayed our Master-Detail pattern on the screen. From here we are ready to use the manager to perform navigation on the Detail Fragment. This will then show the Master-Detail in one of three ways depending on the current device configurations. 

If the Device is a Tablet and is in Landscape it will show as a side-by-side view.
If the Device is a Tablet and is in Portrait it will show the Detail in the main window with the Master hiden until toggled.
If the Device is a Phone in Portrait or Landscape then only the master will be shown at this time.
  
###Presenting a Fragment

In order to present a fragment we follow the same pattern as the SingleStackFragmentManager. From our Master or our detail we can present fragments using the following.

```java
// In the MasterFragment in the MasterDetailExample.
((Button)view.findViewById(R.id.master_btn_add)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        INavigationFragment fragmentToPresent = SampleFragment.newInstance(++fragCount + " Fragment In The Stack.");
        presentFragment(fragmentToPresent);
    }
});
```

While on a Tablet this will transition the Current detail fragment into a new fragment on the stack. This will then cause there to be 2 fragments currently on the Detail view stack. If we are on the Phone it will transition the current view as if it was a single stack fragment. This uses the default animation of slide out to left and slide in from right.

OR if you would like to provide your own animations you can override the next transition by calling the below method before calling `presentFragment`.

```java
@Depreciated
// DEPRECIATED in 0.3.0 to be removed in 0.4.0
// USE method below.
presentFragment(INavigationFragment fragment, int animIn, int animOut);

overrideNextAnimation(int animIn, int animOut);
INavigationFragment fragmentToPresent = SampleFragment.newInstance(++fragCount + " Fragment In The Stack.");
presentFragment(SampleFragment.newInstance(++fragCount + " Fragment In The Stack."));
```

We execute the pending transations so that we can freely grab the fragment immediately after this code is complete and get its current state to manage the Home button in the action bar for use in displaying the Master when the Tablet is in Portrait. We can use the following to manage the actions of showing and hiding the Master when we are in Portrait. Please see below for a method for managing the Action Bar as a possible solution for showing and hiding the Master while in Portrait.

###Dismissing a Fragment

In order to remove fragments from the detail flow we must follow a similar style as presenting. To dismiss the fragments we call the dismissFragment() on the NavigationFragment that we are in. We can use the default animations (slide out right and slide in left).

```java
// Inside the Sample Fragment we use the dismissFragment() method.
((Button)view.findViewById(R.id.sample_btn_back)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dismissFragment();
    }
});
```

OR if you would like to provide your own animations you can override the next transition by calling the below method before calling `dismissFragment`.

```java
@Depreciated
// DEPRECIATED in 0.3.0 to be removed in 0.4.0
// USE method below.
dismissFragment(int animIn, int animOut);

overrideNextAnimation(int animIn, int animOut);
dismissFragment();
```

###Setting the page title

To set the page title on any of the NavigationManagerFragment just call either of the methods for:

```java
setTitle(int resId);
setTitle(String title);
```

This will update the title for the given page.

###Keeping the Back Button Functional

In order to use the back button for dismissing the fragments, we will need to add in some custom code to allow the MasterDetailNavigationManagerFragment to perform its own back action. To do so we will need to get a hold of the MasterDetailNavigationManagerFragment in the onBackPressed and then execute the back action of the Fragment. To do so we use the saved String tag for the MasterDetailNavigationManagerFragment and retrieve it from the FragmentManager. 

```java
// Inside the Main Activity we use the onBackPressed() method.
@Override
public void onBackPressed() {
    NavigationManagerFragment fragment = (NavigationManagerFragment)getSupportFragmentManager().findFragmentByTag(mNavigationManagerFragmentTag);
    if (!fragment.onBackPressed()) {
        super.onBackPressed();
    }
}
```

We can see here that if the onBackPressed() of the MasterDetailNavigationManagerFragment returns false then there are no more fragments in the stack to go back from and hence we perform the default behaviour of the Activity.

As you can see. The Master-Detail navigation fragment works much the same as the Single Stack Manager. This simplefies the use of both, but allows us to display the stacks in a single manner or a side by side manner. 

###Replacing the Root Detail Fragment Directly

In the Master-Detail pattern we can replace the entire Detail stack by calling a single method. This allows us to update the Detail stack to the root without having to dismiss all the other views. In order to do this we simply call.

```java
// Inside the MasterDetailExample Sample Fragment.
((Button)view.findViewById(R.id.master_btn_replace)).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        MasterFragment.this.replaceRootFragment(SampleFragment.newInstance("This is a replaced root Fragment", 0));
    }
});
```

###Managing the Showing and Hiding of the Master while on a Tablet in Portrait

The MasterDetailManagerFragment now handles the showing and hiding of the master whil on a tablet in portrait. All you need to do is set the title of the button shown in the menu

```java
setMasterToggleTitle(String title);
setMasterToggleTitle(int resId);
```

The manager will show and hide the button based on the current fragment that the user is on. It will use the custom title that you have supplied in order to show the item in the menu.

### Additional functionality not noted

####Chaning Default Animations

You can also override the default animations of the `NavigationManagerFragment` so that it will always run your animations. To do so immediately after creating the `NavigationManagerFragment` you can call the following methods

```java
// Call either (or both) of these methods after creating the SingleStack or MasterDetail NavigationManagerFragment. The method should be called on the Manager itself to change the default animations to your defined animations.

public void setDefaultPresentAnimations(int animIn, int animOut);
public void setDefaultDismissAnimations(int animIn, int animOut);
```

These methods will tell the manager to use your defined animations when presenting and dismissing fragments. When you need to use a one time animation change. Use the methods defined in the above sections when presenting/dismissing that allow overriding of the animations on a single transaction.

####MasterDetailNavigationManagerFragment toggle for master page Portrait vs Landscape

When the Master Detail is in Portrait mode the Master will automatically hide. You can have the manager automatically add a button to the action bar to show and hide the Master page when in Portrait. The button is defaulted off but can be added to the action bar with a method on the `MasterDetailNavigationManagerFragment`. After creating the Manager and BEFORE adding it to the screen, call the following method on the manager:

```java
setManageMasterActionBarToggle(boolean manageToggle);
``` 

##Change Log

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
