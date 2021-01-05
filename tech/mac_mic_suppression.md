This took an afternoon to figure out, so I want to preserve it in case it's useful in the future.

Problem: microphone is very sensitive and picks up keyboard, desk bumps, etc.

Solution: maCHiNe lEaRNiNG! Somebody created a [plugin](https://github.com/werman/noise-suppression-for-voice) to suppress background noise (anything other than voice), based on [RNNoise](https://jmvalin.ca/demo/rnnoise/), a project which uses neural networks for noise suppression.
This project supports a variety of plugin standards, but the main one used for Mac OS is VST.

### Building the plugin
On OSX, the plugin needs to be built from source. The [README](https://github.com/werman/noise-suppression-for-voice/blob/master/README.md#compiling) details this process.
You'll have to download the VST2 SDK to support building a VST library. The developers of VST don't host the old VST2 SDK any more, but there's an [archived version](https://archive.org/details/VST2SDK).
When building, the compiler might reject the code because of an implicit cast somewhere. Editing the source to add the cast fixed the issue.


The build produces a Mach-O dylib named `librnnoise_vst.dylib`.
To make the dylib usable, it needs to be bundled into a "loadable bundle" (as described in [this wonderful post](https://github.com/overdrivenpotato/rust-vst2/issues/6#issuecomment-142764469)).
Once in this format, copy it into `/Library/Audio/Plug-Ins/VST/` to install it.

### VST Host application
The job of a VST Host application (at least here) is to take an input device pass its input through the plugin, and output the result to an output device.
There are [many options available](https://bedroomproducersblog.com/2011/05/16/bpb-freeware-studio-best-free-vst-host-applications/), but I had trouble getting most of them working. [Reaper](https://www.reaper.fm/) seems to be the most recommended, and while I got it to work, it's paid software and a full DAW (digital audio workstation), and felt too heavyweight for such a simple purpose.

[Element](https://kushview.net/element/) seems to do the job really well. It's a simple application which lets you arrange devices and plugins using a graphical input-output interface. It's inexepensive ($2) but I wasn't sure if it'd work, so I wanted to try it first.
They have [older releases on their GitHub](https://github.com/kushview/Element/releases/tag/0.41.1) which can be installed directly. I originally tried building from source, but a [bug](https://github.com/kushview/Element/issues/253) prevents the build from acquiring microphone permissions.

Once installed, Element detected the RNNoise plugin automatically.

### Using the suppressed input

In order to use the output of the RNNoise plugin as an input, we need a virtual audio driver. A popular option is [VB-Audio](https://vb-audio.com/Cable/), but I found an open source project called [BlackHole](https://github.com/ExistentialAudio/BlackHole) which does the trick. Once installed, a new BlackHole device becomes available for both input and output.

Within Element, we just need to pass the suppressor output to the BlackHole device, and then configure client applications (e.g., Discord) to use BlackHole as the input device.
