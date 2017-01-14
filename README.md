# Shared Transition Playground

This repo is a proof-of-concept sample, demoing the correct way to do shared element transitions
between two Activities or two Fragments (going from a list to a detail view).

<img src="https://github.com/afollestad/shared-element-transition-samples/raw/master/showcase1.gif"
    width="600" style="max-width: 600px !important" />

# Source Structure

No specific architecture pattern is used...

* `MainActivity` displays the two buttons to either show the `Activity` -> `Activity` sample, or the
`Fragment` -> `Fragment` sample.
* `SourceActivity` is the list `Activity` which transitions into the `TargetActivity` detail screen.
* `FragmentContainerActivity` contains the list `SourceFragment`, which transitions into the
`TargetFragment` detail screen.
* `GlideImageView` is a simple custom `View` that waits to actually load an image until it is measured.
It keeps track of the URL that is set to it, even if it's not ready yet.
* `DetailsTransition` was taken from *Bryan Herbst's* [medium article](https://medium.com/@bherbst/fragment-transitions-with-shared-elements-7c7d71d31cbb#.2j1txydy2), one small part was removed.
* `ImageUtil` is a basic utility class that gets the URL for a target.com thumbnail, in a requested size.
* And of course, `MainAdapter` is used in screens which display a list (`RecyclerView`).