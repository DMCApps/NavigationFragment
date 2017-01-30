#Navigation Manager Fragment

[Slack](https://dmcapps.slack.com/messages/navigationfragment/)

## Author

Daniel Carmo, dcarmo@alumni.uoguelph.ca

##Adding the library to your android studio project

In your app build.gradle file add the following to your dependencies. Project only available on jCenter repository.

```groovy
compile 'com.github.dmcapps:navigation-fragment:2.0.2-alpha'
```

##Current Version

2.0.2-alpha

This version is an alpha. Please send any feedback on the [Issue Tracker](https://github.com/DMCApps/NavigationFragment/issues)

##Migration

###From 1.0.0 to 2.0.0

###MAJOR CHANGE:
The package has been changed from

```groovy 
com.dmcapps.navigationfragment 
```
to
```groovy
com.github.dmcapps.navigationfragment
```

I appologize for the inconvenience but it was necessary to get this into maven and out to more possible users.

In order to ease future development. The update to version 2.0.0 has been a major refactor to remove code duplication between the support and non-support version. This will make future implementations and maintanence much easier.

All the present and dismiss methods work as is. In order to perform more advanced functionality (e.g. overriding animations) you will need to call beginPresentation() first and string together the builder patter items to perform the required tasks.

####Presenting a fragment

```java
// Basic Presenting has not changed. Just call:
presentFragment(Fragment);
// as well as 
presentFramgent(Fragment, Bundle);

// The biggest change is adding additional options to your presentation. In order to present with a bundle or override animations you would do that as follows:
// 1.0.0 
overrideNextAnimation(int, int);
presentFragment(Fragment, Bundle);

// 2.0.0
// Animations must now be set before presentation. They cannot be overriden at dismiss time.
// Presenting a fragment now has additional options and is done through a builder style.
beginPresentation().setCustomAnimations(int, int, int, int)
    .setNavBundle(Bundle)
    .presentFragment(Fragment);
```

####Dismissing a Fragment

```java
// This have not changed. Just call:
dismissFragment();
// OR
dismissFragment(Bundle);
// From within your fragment

// NOTE: animations must all be set at presentation time now and cannot be overridden before a dismiss.
```

####Trasitions
This implementation of the NavigationManager include support for transitions (API 21 and above). See the Transtions example in the v17 project. At this point I have only added the shared element portion as that is all that is required in the transaction. The rest can be set up in the fragments themselves. (FUTURE implementation will do this all in the transaction once set up as well as allow for default implementations much like the )

```java
NavigationFragment fragment = LargeImageFragment.newInstance();
PresentationTransaction transaction = beginPresentation();

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    setExitTransition(new Slide(Gravity.START));
    setEnterTransition(new Slide(Gravity.START));

    fragment.setSharedElementEnterTransition(new DetailTransition());
    fragment.setSharedElementReturnTransition(new DetailTransition());

    fragment.setEnterTransition(new Slide(Gravity.END));

    transaction.addSharedElement(smallImageView, "trans_largeImageView");
}
transaction.presentFragment(fragment);
```

###Items Removed:
MasterDetail implementation (this was created as something that I needed for a project. I've removed it so remove excess coding when adding to the interface declaration)
ListFragment implementation (this is not needed with the recycler view being widely accepted and used and it's been around so long)

##Introduction

The purpose of this manager is to handle a single stack flow of fragments on the screen so that the developer can easily create flows without having to worry about using the FragmentManager and ChildFragmentManager. The single instance of the NavigationManagerFragment will easily handle the presenting and dismissing of Fragments as they are created and added or removed from the stack.

Every Fragment in the Navigation Stack must extend NavigationFragment in order to properly be displayed and navigated. Every NavigationFragment will have access to the NavigationManagerFragment in order to push and pop Fragments from the stack. Further details below will explain how to use the functionality provided by this Manager.

#Implementation

##The Stack Fragment Manager

Use the Stack Fragment Manager just like a normal fragment. In your activity, add it to the manager with an initial fragment and you are ready to use the Navigation Manager.


```java
public class SingleStackNavigationExampleActivity extends AppCompatActivity {

    private static final String STATE_NAV_TAG = "NAV_TAG";

    private String mStackNavigationManagerFragmentTag;

    // ... 

    // In the activity, create the stack manager fragment and add it to the screen with the initial fragment.
    StackNavigationManagerFragment navManager = StackNavigationManagerFragment.newInstance(SampleFragment.newInstance("Root Fragment in the Stack", 0));

    mStackNavigationManagerFragmentTag = UUID.randomUUID().toString();

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.add(android.R.id.content, navManager, mStackNavigationManagerFragmentTag);
    ft.commit();

    // Rest of your code.

```

Now from within your Fragments you can easily present and dismiss fragments using. All fragments that you would like to manager using the NavigationManager must extend `NavigationFragment`.

```java
Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));
presentFragment(fragmentToPresent);
```

Here is an example of the SampleFragment

```java
// You Fragments must extend from NavigationFragment to have access to the helpers and Navigation Manager.
public class SampleFragment extends NavigationFragment {
    
    // ...
    // To perform basic presentations:
    Navigation fragmentToPresent = SampleFragment.newInstance("Fragment added to Stack.", (mFragCount + 1));
    presentFragment(fragmentToPresent);

    // For more advanced presentations use:
    beginPresentation()
        .setCustomAnimations(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top, R.anim.slide_out_to_bottom, R.anim.slide_in_from_top)
        .presentFragment(fragmentToPresent);

    // To dismiss the current fragment call
    dismissFragment();

    // ... Rest of class
}
```

##Upcoming Plans
See [TODO](TODO.md)

##Change Log

###2.0.2
- Updated version for uploading to bintray

###2.0.1
- Added in missed default animation override in the non-support navigation manager

###2.0.0
- Package updated from com.dmcapps.navigationfragment to com.github.dmcapps.navigationfragment this is to prepare for the release to maven.
- Added in Transition support
- Removed default animations. I shouldn't be overriding the default implementation of android fragment navigation. Instead the programmer of the library should call [`NavigationManager.setDefaultPresentAnimations(int animIn, int animOut)`](https://github.com/DMCApps/NavigationFragment/blob/master/navigation-fragment/src/main/java/com/github/dmcapps/navigationfragment/v7/NavigationManagerFragment.java#L79) and [`NavigationManager.setDefaultDismissAnimations(int animIn, int animOut)`](https://github.com/DMCApps/NavigationFragment/blob/master/navigation-fragment/src/main/java/com/github/dmcapps/navigationfragment/v7/NavigationManagerFragment.java#L83)
NOTE: If you would like to add them back in just call NavigationManager.setDefaultPresentAnim(int, int) and setDefaultDismissAnim(int, int) with your animations. The animations are still available under the dmcapp R file as well.
- Major code refactoring to reduce duplicate implementations across support and non-support versions
- Refactored code for future expandability for adding other paramters to each presentation

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
