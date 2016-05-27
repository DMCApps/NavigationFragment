#Change Log

##Future Plans and Examples

###Current Release Features
- COMPLETE - Remove helper method for present/dismiss with animations and force a call to setNextOverrideAnimation instead.
- IN PROGRESS - Passing a bundle in present and dismiss (add the bundle to the existing on under it's own key)

###Next Release
- Library for support and non support fragment

###Future Implementation Notes
- Ability to change the master width
- Add in Acceptance Testing using Robotium.
- Master-Detail additional animations for showing and hiding the master when in portrait
- Master-Detail replace root fragment with an animation and custom animations
- Animation making child not disappear before the animation happens http://stackoverflow.com/a/23276145/845038
- Builder Pattern for NavigationManager
    - NavigationManager.Builder(NavType)
    - setDefaultAnimations()
    - setRootFragment()
    - setMasterFragment()
    - setDetailFragment()
    - setManageMasterToggle()

###TO BE DECIDED/FIGURE OUT
- Find a way to manage the fragments without using setRetainInstance(true) as it makes the child fragment manager not update it's activity.
- https://code.google.com/p/android/issues/detail?id=42601

##Past Versions

###0.2.0.3
- Fixed [Git issue 21](https://github.com/DMCApps/NavigationFragment/issues/21) and [Git issue 22](https://github.com/DMCApps/NavigationFragment/issues/22) 
- State of the navigation manager is no longer lost when configuration changes occur.

###0.2.0.2
- Clear to stack position now executes after completion such that the attached view shows if there is a presentation immediately after.

###0.2.0
- No longer a need for the setNavigationManager(NavigationManagerFragment) method. The NavigationFragmentManager is now smart enough to look at it's parent (or parent's parent, etc.) until it finds a NavigationManagerFragment to use.
- Change ActionBarManager to use the getSupportActionBar() method when setting the title.
- Save the title in a variable and allow retrieval through getTitle().
- Need to protect against getFragmentAtIndex( < 0) by checking and returning null.
- Added in protection against getFragmentAtIndex( > stackSize) by checking and returning null.
- Expose the current stack size.

###0.1.3.1
- Reverted package naming to not cause issues in existing apps

###0.1.3
- Made all micromanagers Serializable
- Updated package naming
- Fixed [Git issue 16](https://github.com/DMCApps/NavigationFragment/issues/16) Issue with getActivity() and mHost not updating in Child Fragments on rotation

###0.1.2.2
- Made the ManagerConfig and ManagerState Serializable classes.

###0.1.2.0
- Update in Manager the `clearStackToPosition` method to not use pop method as it is not appropriate. Allow me to remove multiple fragments in one transaction instead of many transactions.
- Hide master toggle as default. Show if user sets it to be automatic.
- Added in an example for [Git issue 5](https://github.com/DMCApps/NavigationFragment/issues/5) it won't work).
- Added in methods in the NavigationManagerFragment for getting the NavigationFragment at the root, top and at an index.

###0.1.1.1
- Upgraded to use gradle 1.5.0

###0.1.1
- Additional refactoring

###0.1.0
- Refactored library to be more modular
- Updated manager to use internal classes to maintain state
- Updated manager to use lifecycle handlers for the individuals types lifecycle methods.
- Added in functionality to override the default animations (Please see the Override Default Animations Example)

###0.0.5
- Fixed a major bug whenever you had a fragment that contained a property that was not Serializable the manager would crash on startActivity or Home Button Press.

###0.0.4.4
- Master Detail Animation updated to slide in/out when collapsed

###0.0.4.3
- Master Detail Animation toggle now works always.

###0.0.4.2
- Master Detail and Single Stack managers are now fully Serializable.

###0.0.4.1
- Fixed Crash when launching Activity

###0.0.4
- All Navigation types now report their orientation and device type (isPortrait() and isTablet()).

###0.0.3.2
- Fixed popping to root attaching fragments as it removed them causing issues with contexts being null

###0.0.3.1
- Master Detail now reports orientation and device type

###0.0.3
- Title can now be updated from any `NavigationFragment` using setTitle. Activity must be an AppCompatActivity
- Master detail now manages the button that is shown from the Manager
- Manager can accept a title or resource id
- Master Detail now manages the button for showing and hiding itself
- Master Detail now animates to show and hide on button click (only alpha animation. Plans for more in the future)

###0.0.2
- Single Stack Fragment Manager improvements.
- All Managers now use layout files instead of programmatically creating view
- Improvements to the Master-Detail manager
- Master-Detail now shows and hides the Master based on orientation
- Master-Detail now manages fragments appropriately for a phone having only a single stack which starts at the given Master in NewInstance call.
- Added in Example for Tabs (see git for examples).

###0.0.1
- Implementation of SingleStackNavigationManagerFragment
- Handles single stack of fragments pushing and popping in a linear manner.
- Partial implementation of Master-Detail Manager. DOES NOT SUPPORT ORIENTATION CHANGES OR PHONES

##Uploading updates to jCenter and Maven

Complete Set Up with Maven once all approved http://inthecheesefactory.com/blog/how-to-upload-library-to-jcenter-maven-central-as-dependency/en