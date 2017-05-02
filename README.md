# Togglit-Android

**Togglit** is a faster and easier way for companies to keep their students engaged using a customizeable mobile app platform. Anyone on your team can manage and push mobile app content to Togglit users, with no coding required

# Architecture

**Pattern MVP**

What is MVP

The MVP pattern allows separate the presentation layer from the logic, so that everything about how the interface works is separated from how we represent it on screen. Ideally the MVP pattern would achieve that same logic might have completely different and interchangeable views.
First thing to clarify is that MVP is not an architectural pattern, it’s only responsible for the presentation layer . In any case it is always better to use it for your architecture that not using it at all.

**View** 
is a layer that displays data and reacts to user actions. On Android, this could be an Activity, a Fragment, an android.view.View or a Dialog.

**Model**
Is a data access layer such as database API or remote server API.

**Presenter** 
is a layer that provides View with data from Model. Presenter also handles background tasks.
On Android MVP is a way to separate background tasks from activities/views/fragments to make them independent of most lifecycle-related events. This way an application becomes simpler, overall application reliability increases up to 10 times, application code becomes shorter, code maintainability becomes better and developer's life becomes happier.

**The RxJava Android Module**
RxAndroid adds the minimum classes to RxJava that make writing reactive components in Android applications easy and hassle-free.

**AndroidSchedulers**
Is all that remains in RxAndroid, though some method signatures have changed.

The rxjava-android module contains Android-specific bindings for RxJava. It adds a number of classes to RxJava to assist in writing reactive components in Android applications.

-It provides a Scheduler that schedules an Observable on a given Android Handler thread, particularly the main UI thread.

-It provides operators that make it easier to deal with Fragment and Activity life-cycle callbacks.

-It provides wrappers for various Android messaging and notification components so that they can be lifted into an Rx call chain

-It provides reusable, self-contained, reactive components for common Android use cases and UI concerns. (coming soon)


